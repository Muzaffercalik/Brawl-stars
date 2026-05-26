package com.example.ui.viewmodel

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.model.Brawler
import com.example.data.model.BrawlersData
import com.example.data.model.MapConcept
import com.example.data.model.PlayerStats
import com.example.data.model.UnlockedBrawler
import com.example.data.model.UnlockedSkin
import com.example.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    init {
        val database = AppDatabase.getDatabase(application)
        repository = GameRepository(database.gameDao())
        
        // Ensure standard DB data exists
        viewModelScope.launch {
            repository.initializeDatabaseIfNeeded()
        }
    }

    // Static listings
    val staticBrawlers = repository.allStaticBrawlers
    val staticMaps = repository.allStaticMaps

    // Persistent States
    val playerStats: StateFlow<PlayerStats?> = repository.playerStats.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val unlockedBrawlers: StateFlow<List<UnlockedBrawler>> = repository.unlockedBrawlers.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val unlockedSkins: StateFlow<List<UnlockedSkin>> = repository.unlockedSkins.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Screen State
    sealed interface Screen {
        object MainMenu : Screen
        object Wiki : Screen
        object Shop : Screen
        object SimulatorPreview : Screen
        object UnityGuide : Screen
    }

    private val _currentScreen = MutableStateFlow<Screen>(Screen.MainMenu)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    // Currency Actions
    fun buyBrawler(brawlerId: String, cost: Int, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        viewModelScope.launch {
            val success = repository.buyBrawler(brawlerId, cost)
            if (success) onSuccess() else onFailure()
        }
    }

    fun buySkin(skinId: String, brawlerId: String, cost: Int, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        viewModelScope.launch {
            val success = repository.buySkin(skinId, brawlerId, cost)
            if (success) onSuccess() else onFailure()
        }
    }

    fun selectBrawler(brawlerId: String) {
        viewModelScope.launch {
            repository.chooseBrawler(brawlerId)
        }
    }

    fun equipSkin(brawlerId: String, skinId: String) {
        viewModelScope.launch {
            repository.equipSkin(brawlerId, skinId)
        }
    }

    fun upgradeBrawler(brawlerId: String, costGold: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val success = repository.upgradeBrawler(brawlerId, costGold)
            if (success) onSuccess()
        }
    }

    fun unlockForbiddenMode(costEssence: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            val success = repository.unlockForbiddenMode(costEssence)
            if (success) onSuccess()
        }
    }

    fun debugGimmeMoney() {
        viewModelScope.launch {
            repository.debugAddCurrencies()
        }
    }


    // ==========================================
    // COMBAT SIMULATOR ENGINE (PLAYABLE RETRO CANVAS 2D GAMES)
    // ==========================================

    data class SimBot(
        val id: Int,
        val brawlerName: String,
        var name: String,
        var x: Float,
        var y: Float,
        var hp: Float,
        var maxHp: Float,
        var damage: Float,
        var speed: Float,
        var cooldownTimer: Int = 0,
        var isDead: Boolean = false,
        var colorHex: String = "#FFCC00"
    )

    data class BloodSpatter(
        val x: Float,
        val y: Float,
        val radius: Float,
        val opacity: Float,
        val isExtremelyViolent: Boolean
    )

    data class LiveProjectile(
        var x: Float,
        var y: Float,
        val vx: Float,
        val vy: Float,
        val isFromPlayer: Boolean,
        val damage: Float,
        val radius: Float,
        val isSuperUltimate: Boolean = false,
        var active: Boolean = true
    )

    // Match setup state
    val selectedDifficulty = MutableStateFlow("Normal") // Easy, Normal, Hard, Impossible, Psychopath
    val selectedMode = MutableStateFlow("3v3 Solo Arena") // 3v3 Arena, 1v1 Duel, Solo Deathmatch
    val selectedMap = MutableStateFlow(BrawlersData.maps.first())

    // Live Game State
    private val _gameActive = MutableStateFlow(false)
    val gameActive = _gameActive.asStateFlow()

    private val _gameOverState = MutableStateFlow<String?>(null) // "VICTORY" or "DEFEATED"
    val gameOverState = _gameOverState.asStateFlow()

    private val _gameTimeLeft = MutableStateFlow(120) // seconds
    val gameTimeLeft = _gameTimeLeft.asStateFlow()

    private val _playerHp = MutableStateFlow(4000f)
    val playerHp = _playerHp.asStateFlow()

    private val _playerMaxHp = MutableStateFlow(4000f)
    val playerMaxHp = _playerMaxHp.asStateFlow()

    private val _playerSuperCharge = MutableStateFlow(0f) // 0 to 100%
    val playerSuperCharge = _playerSuperCharge.asStateFlow()

    private val _playerUltimateCharge = MutableStateFlow(0f) // 0 to 100%
    val playerUltimateCharge = _playerUltimateCharge.asStateFlow()

    private val _gameKills = MutableStateFlow(0)
    val gameKills = _gameKills.asStateFlow()

    private val _liveBrawler = MutableStateFlow<Brawler?>(null)
    val liveBrawler = _liveBrawler.asStateFlow()

    // 2D Entity Locations (Within 1000 x 1000 digital virtual canvas boundary)
    val playerX = MutableStateFlow(500f)
    val playerY = MutableStateFlow(750f)
    val playerFacingAngle = MutableStateFlow(-1.5708f) // Default looking straight up

    // Advanced dynamic controls & stats
    val speedBoostActive = MutableStateFlow(false)
    val shieldActive = MutableStateFlow(false)
    val gadgetCharges = MutableStateFlow(3)

    // Game Mode Custom State trackers (Gem Grab, Heist, Brawl Ball, Chaos Arena etc)
    val safeHp = MutableStateFlow(10000f)
    val safeMaxHp = MutableStateFlow(10000f)
    val ballX = MutableStateFlow(500f)
    val ballY = MutableStateFlow(500f)
    val ballVx = MutableStateFlow(0f)
    val ballVy = MutableStateFlow(0f)
    val gemCount = MutableStateFlow(0)

    fun updatePlayerFacing(offset: Offset) {
        if (offset.x != 0f || offset.y != 0f) {
            playerFacingAngle.value = kotlin.math.atan2(offset.y, offset.x)
        }
    }

    fun triggerPlayerGadget() {
        if (!_gameActive.value) return
        val currentCharges = gadgetCharges.value
        if (currentCharges > 0) {
            gadgetCharges.value = currentCharges - 1
            viewModelScope.launch {
                speedBoostActive.value = true
                shieldActive.value = true
                _recentScreamMessage.value = "${_liveBrawler.value?.name ?: "Savaşçı"}: \"SÜPER KALKAN VE HIZ GADGETI ETKİNLEŞTİRİLDİ!\""
                delay(4000)
                speedBoostActive.value = false
                shieldActive.value = false
            }
        }
    }

    fun setSelectedMode(mode: String) {
        selectedMode.value = mode
        _recentScreamMessage.value = "YENİ MOD SEÇİLDİ: ${mode.uppercase()}"
        // Reset special elements depending on selected mode
        if (mode == "Heist") {
            safeHp.value = 10000f
        } else if (mode == "Brawl Ball") {
            ballX.value = 500f
            ballY.value = 500f
        } else if (mode == "Gem Grab") {
            gemCount.value = 0
        }
    }

    private val _bots = MutableStateFlow<List<SimBot>>(emptyList())
    val bots = _bots.asStateFlow()

    private val _projectiles = MutableStateFlow<List<LiveProjectile>>(emptyList())
    val projectiles = _projectiles.asStateFlow()

    private val _bloodSplatters = MutableStateFlow<List<BloodSpatter>>(emptyList())
    val bloodSplatters = _bloodSplatters.asStateFlow()

    private val _recentScreamMessage = MutableStateFlow<String?>(null)
    val recentScreamMessage = _recentScreamMessage.asStateFlow()

    private var simLoopJob: Job? = null

    fun startMatch(brawler: Brawler) {
        viewModelScope.launch {
            _liveBrawler.value = brawler

            // Reset advanced features
            playerFacingAngle.value = -1.5708f
            gadgetCharges.value = 3
            speedBoostActive.value = false
            shieldActive.value = false
            safeHp.value = 10000f
            safeMaxHp.value = 10000f
            ballX.value = 500f
            ballY.value = 500f
            gemCount.value = 0

            // Calculate level multiplier
            val dbBrawlers = repository.unlockedBrawlers.firstOrNull() ?: emptyList()
            val matchLevel = dbBrawlers.firstOrNull { it.brawlerId == brawler.id }?.level ?: 1
            val levelMultiplier = 1.0f + (matchLevel - 1) * 0.1f

            _playerMaxHp.value = brawler.hp * levelMultiplier
            _playerHp.value = brawler.hp * levelMultiplier
            _playerSuperCharge.value = 0f
            _playerUltimateCharge.value = 0f
            _gameTimeLeft.value = 120
            _gameKills.value = 0
            _gameOverState.value = null
            _projectiles.value = emptyList()
            _bloodSplatters.value = emptyList()
            _recentScreamMessage.value = "ÇÖL SAVAŞI BAŞLIYOR! KARANLIK GLADYATÖRLER SALDIRIYOR..."

            playerX.value = 500f
            playerY.value = 750f

            // Generate Bots based on select mode and difficulty
            val botCount = when(selectedMode.value) {
                "1v1 Duel" -> 1
                "3v3 Solo Arena" -> 5 // 3 enemy bots, 2 team ally bots (can just simulated free-for-all or target player)
                else -> 6 // Solo Deathmatch
            }

            val difficultyScale = when(selectedDifficulty.value) {
                "Easy" -> 0.6f
                "Normal" -> 1.0f
                "Hard" -> 1.4f
                "Impossible" -> 2.0f
                "Psychopath" -> 3.2f
                else -> 1.0f
            }

            val currentBots = mutableListOf<SimBot>()
            val candidates = staticBrawlers.filter { it.id != brawler.id }

            for (i in 1..botCount) {
                val candidateBrawler = candidates[Random.nextInt(candidates.size)]
                currentBots.add(
                    SimBot(
                        id = i,
                        brawlerName = candidateBrawler.name,
                        name = "Slayer Bot $i",
                        x = Random.nextFloat() * 800f + 100f,
                        y = Random.nextFloat() * 400f + 50f,
                        hp = candidateBrawler.hp * difficultyScale,
                        maxHp = candidateBrawler.hp * difficultyScale,
                        damage = candidateBrawler.damage * difficultyScale * 0.4f,
                        speed = 1.5f + (Random.nextFloat() * 0.5f) * (difficultyScale * 0.6f),
                        colorHex = candidateBrawler.rarity.hexColor
                    )
                )
            }
            _bots.value = currentBots
            _gameActive.value = true

            // Launch Core Simulation updates
            startLoop()
        }
    }

    private fun startLoop() {
        simLoopJob?.cancel()
        simLoopJob = viewModelScope.launch {
            var msCounter = 0
            while (_gameActive.value) {
                delay(30) // ~33 FPS updates
                msCounter += 30

                if (msCounter % 1000 == 0) {
                    if (_gameTimeLeft.value > 0) {
                        _gameTimeLeft.value -= 1
                    } else {
                        endMatch(victory = true) // Outsurvived the clock!
                    }
                }

                // Update Projectiles
                val projList = _projectiles.value.toMutableList()
                val itProj = projList.iterator()
                while (itProj.hasNext()) {
                    val p = itProj.next()
                    p.x += p.vx
                    p.y += p.vy

                    // Out of bounds checks
                    if (p.x < 0 || p.x > 1000 || p.y < 0 || p.y > 1000) {
                        itProj.remove()
                        continue
                    }

                    // Collisions
                    if (p.isFromPlayer) {
                        // Heist Safe collision
                        if (selectedMode.value == "Heist") {
                            val sdx = 500f - p.x
                            val sdy = 250f - p.y
                            val sdist = sqrt(sdx * sdx + sdy * sdy)
                            if (sdist < (60f + p.radius)) {
                                safeHp.value = maxOf(0f, safeHp.value - p.damage)
                                p.active = false
                                spawnBlood(500f, 250f, extViolent = false)
                                if (safeHp.value <= 0f) {
                                    endMatch(victory = true)
                                }
                            }
                        }

                        // Collision check player bullets vs bots
                        for (bot in _bots.value) {
                            if (!bot.isDead) {
                                val dx = bot.x - p.x
                                val dy = bot.y - p.y
                                val dist = sqrt(dx * dx + dy * dy)
                                if (dist < (30f + p.radius)) {
                                    // Bullet hit bot!
                                    bot.hp -= p.damage
                                    p.active = false

                                    // Spawn Blood Spatters (Gore particle effect) Let's make it very violent as requested!
                                    val isViolent = p.isSuperUltimate
                                    spawnBlood(bot.x, bot.y, extViolent = isViolent)

                                    // Increment super Charge on hits
                                    _playerSuperCharge.value = minOf(100f, _playerSuperCharge.value + 6f)
                                    _playerUltimateCharge.value = minOf(100f, _playerUltimateCharge.value + 4f)

                                    if (bot.hp <= 0) {
                                        bot.isDead = true
                                        _gameKills.value += 1
                                        triggerScream(bot.brawlerName)
                                    }
                                    break
                                }
                            }
                        }
                    } else {
                        // Collision check bot bullets vs player
                        val dx = playerX.value - p.x
                        val dy = playerY.value - p.y
                        val dist = sqrt(dx * dx + dy * dy)
                        if (dist < 32f) {
                            _playerHp.value = maxOf(0f, _playerHp.value - p.damage)
                            p.active = false
                            spawnBlood(playerX.value, playerY.value, extViolent = false)

                            if (_playerHp.value <= 0) {
                                endMatch(victory = false)
                            }
                        }
                    }

                    if (!p.active) {
                        itProj.remove()
                    }
                }
                _projectiles.value = projList

                // 1. Brawl Ball physics
                if (selectedMode.value == "Brawl Ball") {
                    val bx = ballX.value + ballVx.value
                    val by = ballY.value + ballVy.value

                    // apply friction
                    ballVx.value *= 0.94f
                    ballVy.value *= 0.94f

                    // Bounces off vertical borders
                    if (bx < 40f) {
                        ballX.value = 40f
                        ballVx.value = -ballVx.value
                    } else if (bx > 960f) {
                        ballX.value = 960f
                        ballVx.value = -ballVx.value
                    } else {
                        ballX.value = bx
                    }

                    // Top Goal / Bottom Goal checks
                    if (by < 40f) {
                        if (bx in 350f..650f) {
                            _recentScreamMessage.value = "GOOOOOOL!!! TAKIMIN HARİKA GOL ATTI!"
                            endMatch(victory = true)
                        } else {
                            ballY.value = 40f
                            ballVy.value = -ballVy.value
                        }
                    } else if (by > 960f) {
                        if (bx in 350f..650f) {
                            _recentScreamMessage.value = "KENDİ KALENE GOL! RAKİP KAZANDI!"
                            endMatch(victory = false)
                        } else {
                            ballY.value = 960f
                            ballVy.value = -ballVy.value
                        }
                    } else {
                        ballY.value = by
                    }
                }

                // 2. Gem Grab Spawner Counter logic (elements gather over time)
                if (selectedMode.value == "Gem Grab" && msCounter % 3000 == 0) {
                    gemCount.value += 1
                    _recentScreamMessage.value = "CENTRAL MİNERAL ORTAYA ÇIKTI! TOPLAM ELEMENT: ${gemCount.value} / 10"
                    if (gemCount.value >= 10) {
                        endMatch(victory = true)
                    }
                }

                // 3. Chaos Arena massive vortex center pull gravity force
                if (selectedMode.value == "Chaos Arena" && msCounter % 150 == 0) {
                    val dx = 500f - playerX.value
                    val dy = 500f - playerY.value
                    val dist = sqrt(dx * dx + dy * dy)
                    if (dist > 15f) {
                        playerX.value += (dx / dist) * 2.5f
                        playerY.value += (dy / dist) * 2.5f
                    }
                    _bots.value.forEach { bot ->
                        if (!bot.isDead) {
                            val bdx = 500f - bot.x
                            val bdy = 500f - bot.y
                            val bdist = sqrt(bdx * bdx + bdy * bdy)
                            if (bdist > 15f) {
                                bot.x += (bdx / bdist) * 2f
                                bot.y += (bdy / bdist) * 2f
                            }
                        }
                    }
                }

                // Keep Alive Bots Intelligence (Simple AI chasing Player)
                val botList = _bots.value
                val diffFactor = when(selectedDifficulty.value) {
                    "Easy" -> 0.05f
                    "Normal" -> 0.08f
                    "Hard" -> 0.12f
                    "Impossible" -> 0.18f
                    "Psychopath" -> 0.35f
                    else -> 0.08f
                }

                for (bot in botList) {
                    if (bot.isDead) continue

                    // Calculate heading direction to player
                    val dx = playerX.value - bot.x
                    val dy = playerY.value - bot.y
                    val dist = sqrt(dx * dx + dy * dy)

                    if (dist > 80) {
                        // Walk towards player
                        bot.x += (dx / dist) * bot.speed
                        bot.y += (dy / dist) * bot.speed
                    } else {
                        // Trigger AI Attack
                        if (bot.cooldownTimer <= 0) {
                            bot.cooldownTimer = 40 // attack lock frames
                            // Shoot projectile straight to player
                            val bProjList = _projectiles.value.toMutableList()
                            bProjList.add(
                                LiveProjectile(
                                    x = bot.x,
                                    y = bot.y,
                                    vx = (dx / dist) * 12f,
                                    vy = (dy / dist) * 12f,
                                    isFromPlayer = false,
                                    damage = bot.damage,
                                    radius = 8f
                                )
                            )
                            _projectiles.value = bProjList
                        }
                    }

                    if (bot.cooldownTimer > 0) {
                        bot.cooldownTimer--
                    }

                    // Psychopath difficulty random dashes
                    if (selectedDifficulty.value == "Psychopath" && Random.nextFloat() < 0.02f) {
                        bot.x += (Random.nextFloat() - 0.5f) * 150f
                        bot.y += (Random.nextFloat() - 0.5f) * 150f
                    }
                }

                // Passive player healing if out of combat (No damage for 3 seconds)
                if (msCounter % 1500 == 0) {
                    _playerHp.value = minOf(_playerMaxHp.value, _playerHp.value + (_playerMaxHp.value * 0.08f))
                }

                // Check victory condition (all dead)
                if (botList.all { it.isDead }) {
                    endMatch(victory = true)
                }

                // Decaying blood opacity style
                val splatters = _bloodSplatters.value.map {
                    it.copy(opacity = maxOf(0f, it.opacity - 0.012f))
                }.filter { it.opacity > 0.01f }
                _bloodSplatters.value = splatters
            }
        }
    }

    private fun spawnBlood(x: Float, y: Float, extViolent: Boolean) {
        val count = if (extViolent) 12 else 4
        val current = _bloodSplatters.value.toMutableList()
        for (i in 0 until count) {
            current.add(
                BloodSpatter(
                    x = x + (Random.nextFloat() - 0.5f) * 60f,
                    y = y + (Random.nextFloat() - 0.5f) * 60f,
                    radius = 8f + Random.nextFloat() * 18f,
                    opacity = 1.0f,
                    isExtremelyViolent = extViolent
                )
            )
        }
        _bloodSplatters.value = current
    }

    private fun triggerScream(killedBrawler: String) {
        val screams = listOf(
            "$killedBrawler: \"AAARRGHHH! ETİM PARÇALANIYOR!\"",
            "$killedBrawler: \"KEMİKLERİM KIRILDI! KAN GÖLÜNDE BOĞULUYORUM!\"",
            "$killedBrawler: \"YASAKLI BÜYÜ RUHUMU EMİYOR! CAN VERDİM!\"",
            "$killedBrawler: \"CELLAT... BIÇAĞI KALBİME SAPLADIN!\"",
            "SPONSOR SESİ: \"DURDURULAMAZ DEHŞET! KILÇIK PARÇALANDI!\""
        )
        _recentScreamMessage.value = screams[Random.nextInt(screams.size)]
    }

    fun handlePlayerMove(offset: Offset) {
        if (!_gameActive.value) return
        val modifierBrawler = _liveBrawler.value ?: return

        val baseSpeed = 9f * modifierBrawler.speed
        val multiplier = if (speedBoostActive.value) 1.5f else 1.0f
        val speed = baseSpeed * multiplier
        val newX = playerX.value + offset.x * speed
        val newY = playerY.value + offset.y * speed

        // Clamp inside canvas bounds
        playerX.value = maxOf(30f, minOf(970f, newX))
        playerY.value = maxOf(30f, minOf(970f, newY))

        // Brawl Ball collision: kick the ball if player collides with it
        if (selectedMode.value == "Brawl Ball") {
            val dx = ballX.value - playerX.value
            val dy = ballY.value - playerY.value
            val dist = sqrt(dx * dx + dy * dy)
            if (dist < 46f) {
                // Kick ball in aiming direction
                val kickAngle = playerFacingAngle.value
                ballVx.value = cos(kickAngle) * 25f
                ballVy.value = sin(kickAngle) * 25f
                _recentScreamMessage.value = "${modifierBrawler.name} TOPA ABANDI!"
            }
        }
    }

    fun triggerPlayerNormalAttack() {
        if (!_gameActive.value) return
        val currentBrawler = _liveBrawler.value ?: return

        val bProjList = _projectiles.value.toMutableList()
        // Fire 3 fan bullet spreads based on independent turning/facing angle!
        val baseAngle = playerFacingAngle.value
        val spreads = if (currentBrawler.id == "kassap") listOf(0f) else listOf(-0.15f, 0f, 0.15f)

        for (spread in spreads) {
            val finalAngle = baseAngle + spread
            bProjList.add(
                LiveProjectile(
                    x = playerX.value,
                    y = playerY.value,
                    vx = cos(finalAngle) * 18f,
                    vy = sin(finalAngle) * 18f,
                    isFromPlayer = true,
                    damage = currentBrawler.damage.toFloat(),
                    radius = 12f
                )
            )
        }
        _projectiles.value = bProjList
    }

    fun triggerPlayerSuperAbility() {
        if (!_gameActive.value || _playerSuperCharge.value < 100f) return
        val currentBrawler = _liveBrawler.value ?: return
        _playerSuperCharge.value = 0f

        val bProjList = _projectiles.value.toMutableList()
        _recentScreamMessage.value = "${currentBrawler.name}: \"${currentBrawler.superName.uppercase()} KULLANILDI!\""

        // Cast massive ultimate ring splash
        for (i in 0 until 12) {
            val angle = (i.toFloat() / 12f) * 6.2831f
            bProjList.add(
                LiveProjectile(
                    x = playerX.value,
                    y = playerY.value,
                    vx = cos(angle) * 10f,
                    vy = sin(angle) * 10f,
                    isFromPlayer = true,
                    damage = currentBrawler.damage.toFloat() * 1.8f,
                    radius = 24f,
                    isSuperUltimate = true
                )
            )
        }
        _projectiles.value = bProjList
        spawnBlood(playerX.value, playerY.value, extViolent = true)
    }

    fun triggerPlayerForbiddenUltimate() {
        if (!_gameActive.value || _playerUltimateCharge.value < 100f) return
        val currentBrawler = _liveBrawler.value ?: return
        _playerUltimateCharge.value = 0f

        val bProjList = _projectiles.value.toMutableList()
        _recentScreamMessage.value = "${currentBrawler.name}: \"${currentBrawler.ultimateName.uppercase()}! RUHLARINIZ BANA AİT!\""

        // Slay everything! Wave screen cleaner
        for (i in 0 until 36) {
            val angle = (i.toFloat() / 36f) * 6.2831f
            bProjList.add(
                LiveProjectile(
                    x = playerX.value,
                    y = playerY.value,
                    vx = cos(angle) * 15f,
                    vy = sin(angle) * 15f,
                    isFromPlayer = true,
                    damage = currentBrawler.damage.toFloat() * 3.5f,
                    radius = 32f,
                    isSuperUltimate = true
                )
            )
        }
        _projectiles.value = bProjList

        // Slam damage on all alive AI bots directly with full screen splatter!
        val liveBots = _bots.value
        for (bot in liveBots) {
            if (!bot.isDead) {
                bot.hp -= (currentBrawler.damage * 4)
                spawnBlood(bot.x, bot.y, extViolent = true)
                if (bot.hp <= 0) {
                    bot.isDead = true
                    _gameKills.value++
                    triggerScream(bot.brawlerName)
                }
            }
        }
    }

    private fun endMatch(victory: Boolean) {
        _gameActive.value = false
        simLoopJob?.cancel()

        val killsCount = _gameKills.value
        val bloodSpatterCount = _bloodSplatters.value.size + (killsCount * 12)

        _gameOverState.value = if (victory) "VICTORY" else "DEFEATED"

        // Persist match stats and give coins to the user local database!
        viewModelScope.launch {
            repository.completeMatch(win = victory, kills = killsCount, bloodShed = bloodSpatterCount)
        }
    }

    fun cancelActiveMatch() {
        _gameActive.value = false
        simLoopJob?.cancel()
        _gameOverState.value = null
        navigateTo(Screen.MainMenu)
    }
}
