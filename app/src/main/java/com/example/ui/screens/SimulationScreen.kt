package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Brawler
import com.example.ui.components.VirtualJoystick
import com.example.ui.viewmodel.GameViewModel

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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080707))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Gameplay Stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0C0C0C))
                    .border(BorderStroke(1.dp, Color(0xFF222222)))
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = mapSelected.title.uppercase(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.3).sp,
                        style = androidx.compose.ui.text.TextStyle(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                    Text(
                        text = "$modeSelected | ZORLUK: $diffSelected",
                        fontSize = 9.sp,
                        color = Color(0xFFFF0000).copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "SÜRE", fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        Text(text = "${timeLeft}s", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "LEŞLER", fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        Text(text = "$kills", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFFFF0000))
                    }
                }

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

            // Screams alert banner (pop-ups in real-time on kills!)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF220A0E))
                    .border(1.dp, Color(0xFFFF003C).copy(alpha = 0.3f))
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = "Scream Log", tint = Color(0xFFFF003C), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = screamLog ?: "RAKİPLERİNİ BUL VE PARÇALA!",
                        fontSize = 11.sp,
                        color = Color(0xFFFFB3B3),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )
                }
            }

            // Central 2D Vector Arena graphics
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF0C0A0B))
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val scaleX = size.width / 1000f
                    val scaleY = size.height / 1000f

                    // Draw floor base
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(mapColor.copy(alpha = 0.35f), Color(0xFF050508)),
                            center = Offset(pX * scaleX, pY * scaleY),
                            radius = size.width * 0.8f
                        )
                    )

                    // Draw static map layouts: chemical lava lakes / pools / walls
                    if (mapSelected.title.contains("Lav") || mapSelected.title.contains("Cehennem")) {
                        // Draw Hell Fire Pool
                        drawCircle(
                            color = Color(0xFFFF5722).copy(alpha = 0.25f),
                            radius = 160f * scaleX,
                            center = Offset(250f * scaleX, 350f * scaleY)
                        )
                        drawCircle(
                            color = Color(0xFFFF5722).copy(alpha = 0.45f),
                            radius = 120f * scaleX,
                            center = Offset(250f * scaleX, 350f * scaleY),
                            style = Stroke(width = 4f)
                        )
                        drawCircle(
                            color = Color(0xFFFF5722).copy(alpha = 0.25f),
                            radius = 160f * scaleX,
                            center = Offset(750f * scaleX, 350f * scaleY)
                        )
                        drawCircle(
                            color = Color(0xFFFF5722).copy(alpha = 0.45f),
                            radius = 120f * scaleX,
                            center = Offset(750f * scaleX, 350f * scaleY),
                            style = Stroke(width = 4f)
                        )
                    } else if (mapSelected.title.contains("Asit") || mapSelected.title.contains("Hastane")) {
                        // Draw Toxic acid pool
                        drawCircle(
                            color = Color(0xFF1DB954).copy(alpha = 0.25f),
                            radius = 180f * scaleX,
                            center = Offset(500f * scaleX, 250f * scaleY)
                        )
                    }

                    // Draw static defense walls
                    val walls = listOf(
                        RectBounds(180f, 600f, 150f, 40f),
                        RectBounds(670f, 600f, 150f, 40f),
                        RectBounds(400f, 400f, 200f, 45f)
                    )
                    for (wall in walls) {
                        drawRoundRect(
                            color = Color(0xFF1E1E24),
                            topLeft = Offset(wall.x * scaleX, wall.y * scaleY),
                            size = Size(wall.w * scaleX, wall.h * scaleY),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f)
                        )
                        drawRoundRect(
                            color = Color(0xFF2C2C35),
                            topLeft = Offset(wall.x * scaleX, wall.y * scaleY),
                            size = Size(wall.w * scaleX, wall.h * scaleY),
                            style = Stroke(width = 2f)
                        )
                    }

                    // Draw blood spatters (violence simulator gore particles!)
                    bloodList.forEach { blood ->
                        drawCircle(
                            color = Color(0xFF900C3F).copy(alpha = blood.opacity),
                            radius = blood.radius * scaleX,
                            center = Offset(blood.x * scaleX, blood.y * scaleY)
                        )
                        if (blood.isExtremelyViolent) {
                            // Splattered jagged chunks
                            drawCircle(
                                color = Color(0xFFFF003C).copy(alpha = blood.opacity * 0.8f),
                                radius = (blood.radius * 0.4f) * scaleX,
                                center = Offset((blood.x + 10) * scaleX, (blood.y - 10) * scaleY)
                            )
                        }
                    }

                    // Draw live AI bot gladiators
                    bots.forEach { bot ->
                        if (!bot.isDead) {
                            // Avatar circle body
                            drawCircle(
                                color = Color(android.graphics.Color.parseColor(bot.colorHex)),
                                radius = 24f * scaleX,
                                center = Offset(bot.x * scaleX, bot.y * scaleY)
                            )
                            drawCircle(
                                color = Color.Black,
                                radius = 24f * scaleX,
                                center = Offset(bot.x * scaleX, bot.y * scaleY),
                                style = Stroke(width = 3f)
                            )

                            // Bot Health stats meter bar
                            val hpRatio = bot.hp / bot.maxHp
                            val barW = 60f * scaleX
                            val barH = 5f * scaleY
                            val barX = (bot.x - 30f) * scaleX
                            val barY = (bot.y - 38f) * scaleY

                            drawRect(
                                color = Color.Gray,
                                topLeft = Offset(barX, barY),
                                size = Size(barW, barH)
                            )
                            drawRect(
                                color = Color(0xFFFF003C),
                                topLeft = Offset(barX, barY),
                                size = Size(barW * hpRatio, barH)
                            )
                        }
                    }

                    // Draw Live player gladiator
                    brawler?.let { b ->
                        val pSize = 28f * scaleX
                        drawCircle(
                            color = Color(0xFFFF003C),
                            radius = pSize,
                            center = Offset(pX * scaleX, pY * scaleY)
                        )
                        drawCircle(
                            color = Color.White,
                            radius = pSize,
                            center = Offset(pX * scaleX, pY * scaleY),
                            style = Stroke(width = 4f)
                        )

                        // Light range pointer
                        val angle = -1.5708f // aiming top direction
                        val length = 80f * scaleX
                        drawLine(
                            color = Color(0xFFFF003C).copy(alpha = 0.5f),
                            start = Offset(pX * scaleX, pY * scaleY),
                            end = Offset(
                                x = (pX + kotlin.math.cos(angle) * length) * scaleX,
                                y = (pY + kotlin.math.sin(angle) * length) * scaleY
                            ),
                            strokeWidth = 6f
                        )
                    }

                    // Draw flying projectiles (flying energy bullets / ultimate waves)
                    projectiles.forEach { proj ->
                        val r = proj.radius * scaleX
                        val color = if (proj.isFromPlayer) {
                            if (proj.isSuperUltimate) Color(0xFFB300FF) else Color(0xFFFF9900)
                        } else {
                            Color(0xFFFFCC00)
                        }

                        drawCircle(
                            color = color,
                            radius = r,
                            center = Offset(proj.x * scaleX, proj.y * scaleY)
                        )
                        if (proj.isSuperUltimate) {
                            // Double aura
                            drawCircle(
                                color = Color.White,
                                radius = r * 0.5f,
                                center = Offset(proj.x * scaleX, proj.y * scaleY)
                            )
                        }
                    }
                }
            }

            // Life stats hud & action controllers overlay
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF131317))
                    .padding(bottom = 24.dp, top = 12.dp, start = 16.dp, end = 16.dp)
            ) {
                // Lifemeter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = (brawler?.name ?: "Gladyatör") + " SAĞLIK",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${playerHp.toInt()} / ${playerMaxHp.toInt()}",
                        fontSize = 11.sp,
                        color = Color(0xFF1DB954),
                        fontWeight = FontWeight.Black
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = if (playerMaxHp > 0) playerHp / playerMaxHp else 0f,
                    color = Color(0xFF1DB954),
                    trackColor = Color(0xFF2C2C35),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Control panel block
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Twin Joystick Left
                    VirtualJoystick(
                        size = 130.dp,
                        onValueChange = { offset ->
                            viewModel.handlePlayerMove(offset)
                        }
                    )

                    // Action buttons (Normal Attack, Super, Ultimate) Right
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // Normal Fire Attack button
                            RoundActionButton(
                                label = "SALDIR",
                                subtitle = "Normal",
                                value = 1f,
                                max = 1f,
                                color = Color(0xFFFF5722),
                                onClick = { viewModel.triggerPlayerNormalAttack() }
                            )

                            // Super Skill (Yellow)
                            RoundActionButton(
                                label = "SÜPER",
                                subtitle = brawler?.superName?.take(7) ?: "Yetenek",
                                value = superCharge,
                                max = 100f,
                                color = Color(0xFFFFD700),
                                activeColor = Color(0xFFB300FF),
                                onClick = { viewModel.triggerPlayerSuperAbility() }
                            )
                        }

                        // FORBIDDEN UNLEASHED ULTIMATE (Purple/Black)
                        val forbiddenUnlocked = stats?.forbiddenModeUnlocked == true
                        Box(
                            modifier = Modifier
                                .width(184.dp)
                                .height(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (forbiddenUnlocked && ultimateCharge >= 100f) Color(0xFF5A009C)
                                    else if (forbiddenUnlocked) Color(0xFF20132C)
                                    else Color(0xFF15151A)
                                )
                                .border(
                                    1.dp,
                                    if (forbiddenUnlocked && ultimateCharge >= 100f) Color(0xFFB300FF)
                                    else Color(0xFF2C2C35),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = forbiddenUnlocked && ultimateCharge >= 100f) {
                                    viewModel.triggerPlayerForbiddenUltimate()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (forbiddenUnlocked) {
                                Text(
                                    text = if (ultimateCharge >= 100f) "🔥 FORBIDDEN ULTIMATE HAZIR! 🔥"
                                    else "FORBIDDEN ULTIMATE: ${ultimateCharge.toInt()}%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (ultimateCharge >= 100f) Color.White else Color(0x99B300FF)
                                )
                            } else {
                                Text(
                                    text = "🔒 FORBIDDEN ULTIMATE KİLİTLİ",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6E6E77)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Victory / Defeat outcome overlays
        gameOver?.let { outcome ->
            val victory = outcome == "VICTORY"
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xE6050508)),
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
                            text = if (victory) "Tüm offline yapay zeka rakipleri arena kumlarına gömdün."
                            else "Rakipler bedenini parçalayıp ruhunu feda ettiler.",
                            fontSize = 12.sp,
                            color = Color(0xFF8A8A93),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Rewards breakdown
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

@Composable
fun RoundActionButton(
    label: String,
    subtitle: String,
    value: Float,
    max: Float,
    color: Color,
    activeColor: Color = Color.White,
    onClick: () -> Unit
) {
    val full = value >= max
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(if (full) activeColor else color.copy(alpha = 0.15f))
            .border(
                2.dp,
                if (full) Color.White else color.copy(alpha = 0.5f),
                CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (full) Color.Black else Color.White
            )
            Text(
                text = if (full) "AKTİF" else "${value.toInt()}%",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (full) Color.Black else color
            )
        }
    }
}

data class RectBounds(val x: Float, val y: Float, val w: Float, val h: Float)
