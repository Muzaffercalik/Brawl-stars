package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Brawler
import com.example.ui.components.VirtualJoystick
import com.example.ui.viewmodel.GameViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimulationScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val brawler by viewModel.liveBrawler.collectAsState()
    val activeState by viewModel.gameActive.collectAsState()
    val gameOver by viewModel.gameOverState.collectAsState()
    val stats by viewModel.playerStats.collectAsState()

    val mapSelected by viewModel.selectedMap.collectAsState()
    val modeSelected by viewModel.selectedMode.collectAsState()
    val diffSelected by viewModel.selectedDifficulty.collectAsState()

    val timeLeft by viewModel.gameTimeLeft.collectAsState()
    val kills by viewModel.gameKills.collectAsState()
    val playerHp by viewModel.playerHp.collectAsState()
    val playerMaxHp by viewModel.playerMaxHp.collectAsState()
    val superCharge by viewModel.playerSuperCharge.collectAsState()
    val ultimateCharge by viewModel.playerUltimateCharge.collectAsState()

    // Real-time custom state mechanics
    val facingAngle by viewModel.playerFacingAngle.collectAsState()
    val speedBoost by viewModel.speedBoostActive.collectAsState()
    val shieldActive by viewModel.shieldActive.collectAsState()
    val gadgetCharges by viewModel.gadgetCharges.collectAsState()
    val safeHp by viewModel.safeHp.collectAsState()
    val safeMaxHp by viewModel.safeMaxHp.collectAsState()
    val ballX by viewModel.ballX.collectAsState()
    val ballY by viewModel.ballY.collectAsState()
    val gemCount by viewModel.gemCount.collectAsState()

    val pX by viewModel.playerX.collectAsState()
    val pY by viewModel.playerY.collectAsState()
    val bots by viewModel.bots.collectAsState()
    val projectiles by viewModel.projectiles.collectAsState()
    val bloodList by viewModel.bloodSplatters.collectAsState()
    val screamLog by viewModel.recentScreamMessage.collectAsState()

    val mapColor = remember(mapSelected) {
        try {
            Color(android.graphics.Color.parseColor(mapSelected.primaryColorHex))
        } catch (e: Exception) {
            Color(0xFF7A0010) // default Crimson blood
        }
    }

    val modesList = remember {
        listOf(
            "Gem Grab", "Bounty", "Heist", "Showdown",
            "Brawl Ball", "Hot Zone", "Knockout", "Chaos Arena"
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080707))
    ) {
        // Main Immersive Full-Bleed Content Column
        Column(modifier = Modifier.fillMaxSize()) {

            // SECTION 1: Game Modes selection bar (Highly visible selectable tabs at top)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0C0C0C))
                    .statusBarsPadding()
                    .border(BorderStroke(1.dp, Color(0xFF222222)))
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "AKTİF SAVAŞ MODLARI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 2.sp,
                    style = androidx.compose.ui.text.TextStyle(
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(modesList) { mode ->
                        val isActive = mode == modeSelected
                        val modeGradient = if (isActive) {
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFCC0000), Color(0xFF770000))
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF161616), Color(0xFF161616))
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(modeGradient)
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (isActive) Color(0xFFFF0000).copy(alpha = 0.6f) else Color(0xFF333333)
                                    ),
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { viewModel.setSelectedMode(mode) }
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                when (mode) {
                                    "Gem Grab" -> Icon(Icons.Default.Star, contentDescription = "Gem", tint = Color(0xFFB300FF), modifier = Modifier.size(11.dp))
                                    "Heist" -> Icon(Icons.Default.Lock, contentDescription = "Safe", tint = Color(0xFFFFCC00), modifier = Modifier.size(11.dp))
                                    "Brawl Ball" -> Icon(Icons.Default.PlayArrow, contentDescription = "Ball", tint = Color.White, modifier = Modifier.size(11.dp))
                                    "Chaos Arena" -> Icon(Icons.Default.Warning, contentDescription = "Chaos", tint = Color.Red, modifier = Modifier.size(11.dp))
                                    else -> Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.Gray, modifier = Modifier.size(11.dp))
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = mode.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isActive) Color.White else Color.Gray,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }

            // SECTION 2: LIVE METERS & MINIMAP HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF070707))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Column: Player quick facts
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = (brawler?.name ?: "Gladyatör").uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp,
                        style = androidx.compose.ui.text.TextStyle(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "HARİTA: " + mapSelected.title.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // Health bar
                    Row(
                        modifier = Modifier.width(180.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LinearProgressIndicator(
                            progress = if (playerMaxHp > 0) playerHp / playerMaxHp else 0f,
                            color = Color(0xFF00FFCC),
                            trackColor = Color(0xFF222222),
                            modifier = Modifier
                                .weight(1f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${playerHp.toInt()} HP",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF00FFCC)
                        )
                    }
                }

                // Middle Stats (Timer & Score Kills)
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "SÜRE", fontSize = 7.sp, color = Color.Gray, fontWeight = FontWeight.Black)
                        Text(text = "${timeLeft}s", fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "LEŞLER", fontSize = 7.sp, color = Color.Gray, fontWeight = FontWeight.Black)
                        Text(text = "$kills", fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color(0xFFFF003C))
                    }
                }

                // Right HUD element: CIRCULAR DYNAMIC MINIMAP IN THE CORNER
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF101014))
                        .border(1.5.dp, Color(0xFFFF003C).copy(alpha = 0.4f), CircleShape)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val mr = size.width / 2f
                        val mapCenter = Offset(mr, mr)

                        // Draw background radar sweep rings
                        drawCircle(Color(0x1A00FFCC), radius = mr * 0.8f, center = mapCenter, style = Stroke(1f))
                        drawCircle(Color(0x15FF003C), radius = mr * 0.5f, center = mapCenter, style = Stroke(1f))

                        // Draw center mode anchor
                        if (modeSelected == "Gem Grab" || modeSelected == "Chaos Arena") {
                            drawCircle(Color(0xFFB300FF), radius = 4f, center = mapCenter) // Portal / Mine central point
                        } else if (modeSelected == "Heist") {
                            val safeDotY = mr + (250f - 500f) / 1000f * (mr * 1.6f)
                            drawCircle(Color(0xFFFFCC00), radius = 4f, center = Offset(mapCenter.x, safeDotY))
                        } else if (modeSelected == "Brawl Ball") {
                            val ballDotX = mr + (ballX - 500f) / 1000f * (mr * 1.6f)
                            val ballDotY = mr + (ballY - 500f) / 1000f * (mr * 1.6f)
                            drawCircle(Color.White, radius = 3.5f, center = Offset(ballDotX, ballDotY))
                        }

                        // Draw Player Node Dot scaled (Green dot)
                        val pDotX = mr + (pX - 500f) / 1000f * (mr * 1.6f)
                        val pDotY = mr + (pY - 500f) / 1000f * (mr * 1.6f)
                        drawCircle(Color(0xFF00FFCC), radius = 5f, center = Offset(pDotX, pDotY))

                        // Draw Enemy Bots Dots (Red dots)
                        bots.forEach { bot ->
                            if (!bot.isDead) {
                                val bDotX = mr + (bot.x - 500f) / 1000f * (mr * 1.6f)
                                val bDotY = mr + (bot.y - 500f) / 1000f * (mr * 1.6f)
                                drawCircle(Color(0xFFFF003C), radius = 3.5f, center = Offset(bDotX, bDotY))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = { viewModel.cancelActiveMatch() },
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FF0000))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Exit", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }

            // Screams alert banner for interactive atmosphere
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E0709))
                    .border(1.dp, Color(0xFFFF003C).copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = "Radio", tint = Color(0xFFFF003C), modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = screamLog ?: "MEYDAN OKUMAYI TAMAMLA & PARLAK ZAFERE ULAŞ!",
                        fontSize = 11.sp,
                        color = Color(0xFFFF7788),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // SECTION 3: CORE DETAILED 2D ARENA DRAWINGS
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF0C0C0E))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val scaleX = size.width / 1000f
                    val scaleY = size.height / 1000f

                    // 1. Cybernetic Floor Design
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(mapColor.copy(alpha = 0.35f), Color(0xFF070707)),
                            center = Offset(pX * scaleX, pY * scaleY),
                            radius = size.width * 0.82f
                        )
                    )

                    // Draw grid map guides
                    for (i in 1..9) {
                        val gx = (i * 100f) * scaleX
                        val gy = (i * 100f) * scaleY
                        drawLine(Color(0x16FFFFFF), Offset(gx, 0f), Offset(gx, size.height), strokeWidth = 1f)
                        drawLine(Color(0x16FFFFFF), Offset(0f, gy), Offset(size.width, gy), strokeWidth = 1f)
                    }

                    // 2. Mode Specific Centerpiece entities
                    // Mode A: CHAOS ARENA pulsing black vortex
                    if (modeSelected == "Chaos Arena") {
                        drawCircle(
                            color = Color(0xFF1E0005).copy(alpha = 0.65f),
                            radius = 260f * scaleX,
                            center = Offset(500f * scaleX, 500f * scaleY)
                        )
                        drawCircle(
                            color = Color(0xFFFF003C).copy(alpha = 0.2f),
                            radius = 160f * scaleX,
                            center = Offset(500f * scaleX, 500f * scaleY)
                        )
                        drawCircle(
                            color = Color(0xFFFF0000).copy(alpha = 0.4f),
                            radius = 100f * scaleX,
                            center = Offset(500f * scaleX, 500f * scaleY),
                            style = Stroke(width = 8f)
                        )
                        drawCircle(
                            color = Color.Black,
                            radius = 45f * scaleX,
                            center = Offset(500f * scaleX, 500f * scaleY)
                        )
                    }

                    // Mode B: GEM GRAB mine crystal portal
                    if (modeSelected == "Gem Grab") {
                        drawCircle(
                            color = Color(0xFF220044).copy(alpha = 0.4f),
                            radius = 120f * scaleX,
                            center = Offset(500f * scaleX, 500f * scaleY)
                        )
                        drawRoundRect(
                            color = Color(0xFFB300FF),
                            topLeft = Offset(465f * scaleX, 465f * scaleY),
                            size = Size(70f * scaleX, 70f * scaleY),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f)
                        )
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(475f * scaleX, 475f * scaleY),
                            size = Size(50f * scaleX, 50f * scaleY),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f),
                            style = Stroke(width = 4f)
                        )
                    }

                    // Mode C: HEIST Safe Vault at (500, 250)
                    if (modeSelected == "Heist") {
                        // Drawing Safe Vault
                        val sw = 160f * scaleX
                        val sh = 100f * scaleY
                        val sx = 420f * scaleX
                        val sy = 200f * scaleY
                        drawRoundRect(
                            color = Color(0xFF26262B),
                            topLeft = Offset(sx, sy),
                            size = Size(sw, sh),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f)
                        )
                        drawRoundRect(
                            color = Color(0xFFFFCC00),
                            topLeft = Offset(sx, sy),
                            size = Size(sw, sh),
                            style = Stroke(width = 3.dp.toPx()),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f)
                        )
                        drawCircle(color = Color.Yellow, radius = 22f * scaleX, center = Offset(500f * scaleX, 250f * scaleY))
                        drawCircle(color = Color.Black, radius = 8f * scaleX, center = Offset(500f * scaleX, 250f * scaleY))

                        // Safe health text & bar
                        val safeHpRatio = safeHp / safeMaxHp
                        val sBarW = 120f * scaleX
                        val sBarH = 8f * scaleY
                        val sBarX = 440f * scaleX
                        val sBarY = 160f * scaleY

                        drawRect(Color.Gray, topLeft = Offset(sBarX, sBarY), size = Size(sBarW, sBarH))
                        drawRect(Color(0xFFFFCC00), topLeft = Offset(sBarX, sBarY), size = Size(sBarW * safeHpRatio, sBarH))
                    }

                    // Mode D: BRAWL BALL soccer sphere bouncing around
                    if (modeSelected == "Brawl Ball") {
                        val ballPx = ballX * scaleX
                        val ballPy = ballY * scaleY
                        // Ball shadow
                        drawCircle(Color(0x7F000000), radius = 22f * scaleX, center = Offset(ballPx + 5f, ballPy + 5f))
                        // Ball Body
                        drawCircle(Color.White, radius = 20f * scaleX, center = Offset(ballPx, ballPy))
                        drawCircle(Color.Black, radius = 20f * scaleX, center = Offset(ballPx, ballPy), style = Stroke(width = 3f))
                        // Pentagons patterns
                        drawCircle(Color.Black, radius = 7f * scaleX, center = Offset(ballPx, ballPy))
                    }

                    // 3. Static Obstacles Layouts
                    if (mapSelected.title.contains("Lav") || mapSelected.title.contains("Cehennem")) {
                        drawCircle(Color(0xFFFF4500).copy(alpha = 0.3f), radius = 150f * scaleX, center = Offset(250f * scaleX, 350f * scaleY))
                        drawCircle(Color(0xFFFF8800).copy(alpha = 0.3f), radius = 150f * scaleX, center = Offset(750f * scaleX, 350f * scaleY))
                    } else if (mapSelected.title.contains("Asit") || mapSelected.title.contains("Hastane")) {
                        drawCircle(Color(0xFF00FF3C).copy(alpha = 0.25f), radius = 170f * scaleX, center = Offset(500f * scaleX, 250f * scaleY))
                    }

                    // Static defense walls structures
                    val boundaryWalls = listOf(
                        RectBounds(180f, 600f, 150f, 40f),
                        RectBounds(670f, 600f, 150f, 40f),
                        RectBounds(400f, 400f, 200f, 45f)
                    )
                    boundaryWalls.forEach { wall ->
                        drawRoundRect(
                            color = Color(0xFF1E1E24),
                            topLeft = Offset(wall.x * scaleX, wall.y * scaleY),
                            size = Size(wall.w * scaleX, wall.h * scaleY),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f)
                        )
                        drawRoundRect(
                            color = Color(0xFFFF003C).copy(alpha = 0.4f),
                            topLeft = Offset(wall.x * scaleX, wall.y * scaleY),
                            size = Size(wall.w * scaleX, wall.h * scaleY),
                            style = Stroke(width = 2f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f)
                        )
                    }

                    // 4. Violence Gore Blood Splatters Splashes
                    bloodList.forEach { blood ->
                        drawCircle(
                            color = Color(0xFF990000).copy(alpha = blood.opacity),
                            radius = blood.radius * scaleX,
                            center = Offset(blood.x * scaleX, blood.y * scaleY)
                        )
                    }

                    // 5. Enemy AI Gladiators Drawing
                    bots.forEach { bot ->
                        if (!bot.isDead) {
                            val br = 24f * scaleX
                            val botCenter = Offset(bot.x * scaleX, bot.y * scaleY)
                            // Outer shadow
                            drawCircle(Color(0x40000000), radius = br * 1.2f, center = botCenter)
                            // Body
                            drawCircle(Color(android.graphics.Color.parseColor(bot.colorHex)), radius = br, center = botCenter)
                            drawCircle(Color.Black, radius = br, center = botCenter, style = Stroke(width = 3f))

                            // Facing pointer indicator
                            drawLine(
                                color = Color.White.copy(alpha = 0.7f),
                                start = botCenter,
                                end = Offset(botCenter.x, botCenter.y + 35f * scaleX),
                                strokeWidth = 4f
                            )

                            // Health indicator bar
                            val ratio = bot.hp / bot.maxHp
                            val bw = 64f * scaleX
                            val bh = 6f * scaleY
                            val bx = (bot.x - 32f) * scaleX
                            val by = (bot.y - 36f) * scaleY
                            drawRect(Color.Gray, topLeft = Offset(bx, by), size = Size(bw, bh))
                            drawRect(Color(0xFFFF003C), topLeft = Offset(bx, by), size = Size(bw * ratio, bh))
                        }
                    }

                    // 6. MAIN CUSTOMIZABLE FIGHTER CHARACTER IN THE CENTER
                    brawler?.let { b ->
                        val pSize = 30f * scaleX
                        val playerCenter = Offset(pX * scaleX, pY * scaleY)

                        // Glowing select ring underneath
                        val selectColor = if (shieldActive) Color(0xFF00FFCC).copy(alpha = 0.5f) else Color(0xFFFF003C).copy(alpha = 0.35f)
                        drawCircle(
                            color = selectColor,
                            radius = pSize * 1.5f,
                            center = playerCenter
                        )

                        // Main core geometric body
                        drawCircle(Color(0xFF16161C), radius = pSize, center = playerCenter)
                        drawCircle(Color.White, radius = pSize, center = playerCenter, style = Stroke(width = 4f))

                        // Inner symbol details (Fighter emblem)
                        drawCircle(Color(0xFFFF003C), radius = pSize * 0.4f, center = playerCenter)

                        // 3RD JOYSTICK FACING/STRAFING ANGLE RAY POINTER!
                        // This proves the dedicated direction controls works: independent pointing ray!
                        val pointerLength = 85f * scaleX
                        val endX = pX + kotlin.math.cos(facingAngle) * pointerLength
                        val endY = pY + kotlin.math.sin(facingAngle) * pointerLength
                        val pointerEnd = Offset(endX * scaleX, endY * scaleY)

                        // Aim guide line
                        drawLine(
                            color = Color(0xFF00FFCC).copy(alpha = 0.8f),
                            start = playerCenter,
                            end = pointerEnd,
                            strokeWidth = 6f
                        )
                        // Aim tip target crosshair dot
                        drawCircle(
                            color = Color(0xFF00FFCC),
                            radius = 6f * scaleX,
                            center = pointerEnd
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 12f * scaleX,
                            center = pointerEnd,
                            style = Stroke(width = 2f)
                        )

                        // Active shield bubble force-field
                        if (shieldActive) {
                            drawCircle(
                                color = Color(0xFF00FFCC).copy(alpha = 0.22f),
                                radius = pSize * 1.9f,
                                center = playerCenter
                            )
                            drawCircle(
                                color = Color(0xFF00FFCC).copy(alpha = 0.6f),
                                radius = pSize * 1.9f,
                                center = playerCenter,
                                style = Stroke(width = 3f)
                            )
                        }
                    }

                    // 7. Projectiles (ammo shots/energy beams)
                    projectiles.forEach { proj ->
                        val pr = proj.radius * scaleX
                        val pcolor = if (proj.isFromPlayer) {
                            if (proj.isSuperUltimate) Color(0xFFB300FF) else Color(0xFF00FFCC)
                        } else {
                            Color(0xFFFFB300)
                        }
                        // Inner core
                        drawCircle(pcolor, radius = pr, center = Offset(proj.x * scaleX, proj.y * scaleY))
                        drawCircle(Color.White, radius = pr * 0.5f, center = Offset(proj.x * scaleX, proj.y * scaleY))
                    }
                }
            }

            // SECTION 4: CONTROL CONSOLE OVERLAY (Semi-transparent ergonomic handheld interface)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF101014))
                    .padding(bottom = 24.dp, top = 14.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    
                    // JOYSTICK 1 (Left): movement classic translucent controller
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "HAREKET JOYSTICK",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        VirtualJoystick(
                            modifier = Modifier
                                .testTag("movement_joystick")
                                .shadow(8.dp, CircleShape),
                            size = 110.dp,
                            onValueChange = { offset ->
                                viewModel.handlePlayerMove(offset)
                            }
                        )
                    }

                    // MIDDLE SECTION: COOL DETAILED SKILLS TRIGGER CLUSTER
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            // ACTIVE SKILL 1: GADGET Tech (Utility boost speeds & shields)
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (gadgetCharges > 0) Color(0xFF006655).copy(alpha = 0.4f)
                                        else Color(0xFF222226)
                                    )
                                    .border(
                                        2.dp,
                                        if (gadgetCharges > 0) Color(0xFF00FFCC) else Color(0xFF444444),
                                        CircleShape
                                    )
                                    .clickable(enabled = gadgetCharges > 0) {
                                        viewModel.triggerPlayerGadget()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Build, contentDescription = "Gadget", tint = if (gadgetCharges > 0) Color(0xFF00FFCC) else Color.Gray, modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "$gadgetCharges/3",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (gadgetCharges > 0) Color.White else Color.Gray
                                    )
                                }
                            }

                            // ACTIVE SKILL 2: SUPER Attack Ability (Yellow supercharge)
                            val isSuperReady = superCharge >= 100f
                            Box(
                                modifier = Modifier
                                    .size(62.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSuperReady) Color(0xFF996600).copy(alpha = 0.4f)
                                        else Color(0xFF222226)
                                    )
                                    .border(
                                        2.5.dp,
                                        if (isSuperReady) Color(0xFFFFCC00) else Color(0xFF444444),
                                        CircleShape
                                    )
                                    .clickable(enabled = isSuperReady) {
                                        viewModel.triggerPlayerSuperAbility()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = "Super",
                                        tint = if (isSuperReady) Color(0xFFFFCC00) else Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = if (isSuperReady) "HAZIR" else "${superCharge.toInt()}%",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isSuperReady) Color.White else Color.Gray
                                    )
                                }
                            }
                        }

                        // FORBIDDEN DEMONIC ULTIMATE (Wide glowing bar at center bottom)
                        val matchesStats = stats?.forbiddenModeUnlocked == true
                        val readyU = ultimateCharge >= 100f
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (matchesStats && readyU) Color(0xFF440066)
                                    else if (matchesStats) Color(0xFF1E1325)
                                    else Color(0xFF141416)
                                )
                                .border(
                                    1.dp,
                                    if (matchesStats && readyU) Color(0xFFB300FF) else Color(0xFF333336),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable(enabled = matchesStats && readyU) {
                                    viewModel.triggerPlayerForbiddenUltimate()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (matchesStats) {
                                Text(
                                    text = if (readyU) "🔥 FORBIDDEN ULTIMATE HAZIR! 🔥" else "ULTIMATE: ${ultimateCharge.toInt()}%",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (readyU) Color.White else Color(0x99B300FF),
                                    letterSpacing = 1.sp
                                )
                            } else {
                                Text(
                                    text = "🔒 FORBIDDEN ULTIMATE KİLİTLİ",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    // JOYSTICK 2 & 3 (Right hand): Dual Stacked direction joysticks!
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        
                        // JOYSTICK 3 (Top right): Dedicated turning strafing controller
                        // Allows setting looking angle independent of movement!
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "BAKIŞ / STRAFE YÖNÜ",
                                fontSize = 7.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00FFCC),
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                            VirtualJoystick(
                                modifier = Modifier
                                    .testTag("turning_joystick")
                                    .size(75.dp)
                                    .shadow(4.dp, CircleShape),
                                size = 75.dp,
                                onValueChange = { offset ->
                                    viewModel.updatePlayerFacing(offset)
                                }
                            )
                        }

                        // JOYSTICK 2 (Bottom right): Main Action/Aim shooting joystick
                        // Aim and instant fire!
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "SALDIRI / HEDEF AL",
                                fontSize = 7.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF003C),
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                            VirtualJoystick(
                                modifier = Modifier
                                    .testTag("action_joystick")
                                    .shadow(6.dp, CircleShape),
                                size = 95.dp,
                                onValueChange = { offset ->
                                    viewModel.updatePlayerFacing(offset)
                                    if (offset.x != 0f || offset.y != 0f) {
                                        viewModel.triggerPlayerNormalAttack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // Victory / Defeat outcome card alert modal
        gameOver?.let { outcome ->
            val victory = outcome == "VICTORY"
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xE6050508))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
                    border = BorderStroke(2.dp, if (victory) Color(0xFF1DB954) else Color(0xFFFF003C))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (victory) "ARENA ZAFERİ!" else "GLADYATÖR CAN VERDİ!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = if (victory) Color(0xFF1DB954) else Color(0xFFFF003C),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (victory) "Seçtiğin gladyatör yapay zeka rakipleri dize getirdi ve rüştünü kanıtladı!"
                            else "Rakipler daha çevik davrandı ve seni arenaya gömdü.",
                            fontSize = 12.sp,
                            color = Color(0xFF8A8A93),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Rewards panel
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0C0C10))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "KAZANILAN ALTIN", fontSize = 8.sp, color = Color.Gray)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "Altın", tint = Color(0xFFFFD700), modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = if (victory) "+150" else "+50", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "SHADOW ESSENCE", fontSize = 8.sp, color = Color.Gray)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "Essence", tint = Color(0xFFB300FF), modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = if (victory) "+20" else "+${5 + (kills * 2)}", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.cancelActiveMatch() },
                            colors = ButtonDefaults.buttonColors(containerColor = if (victory) Color(0xFF1DB954) else Color(0xFFFF003C)),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(text = "MENÜYE DÖN", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

data class RectBounds(val x: Float, val y: Float, val w: Float, val h: Float)
