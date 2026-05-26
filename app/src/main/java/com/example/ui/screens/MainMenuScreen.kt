package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Brawler
import com.example.ui.viewmodel.GameViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainMenuScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.playerStats.collectAsState()
    val brawlersList by viewModel.unlockedBrawlers.collectAsState()

    val currentBrawler = remember(stats, viewModel.staticBrawlers) {
        val id = stats?.chosenBrawlerId ?: "kassap"
        viewModel.staticBrawlers.firstOrNull { it.id == id } ?: viewModel.staticBrawlers.first()
    }

    val activeBrawlerProg = remember(brawlersList, currentBrawler) {
        brawlersList.firstOrNull { it.brawlerId == currentBrawler.id }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080707))
    ) {
        // Massive Background Typography
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SHADOW",
                fontSize = 110.sp,
                fontWeight = FontWeight.Black,
                color = Color(0x06FFFFFF),
                lineHeight = 90.sp,
                letterSpacing = (-4).sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "BRAWL",
                fontSize = 110.sp,
                fontWeight = FontWeight.Black,
                color = Color(0x06FFFFFF),
                lineHeight = 90.sp,
                letterSpacing = (-4).sp,
                textAlign = TextAlign.Center
            )
        }

        // Decorative background particles
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Game Logo Display
            Text(
                text = "SHADOW BRAWL",
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = (-1.5).sp,
                style = androidx.compose.ui.text.TextStyle(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            )
            Text(
                text = "ETERNAL ARENA",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFF0000),
                letterSpacing = 6.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Top Currency/Level Bar (HTML Simulation)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Rank Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF161616))
                        .border(1.dp, Color(0xFF333333), RoundedCornerShape(24.dp))
                        .padding(start = 4.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF880808), Color(0xFFFF0000))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "42",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "GLADIATOR RANK",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                        Text(
                            text = "BLOOD LORD",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 10.sp
                        )
                    }
                }

                // Right Currencies Badges
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gold Meter
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF161616))
                            .border(1.dp, Color(0xFF333333), RoundedCornerShape(24.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${stats?.gold ?: 0}",
                            color = Color(0xFFFF4E00),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color(0xFFFF4E00))
                        )
                    }

                    // Essence Meter
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF161616))
                            .border(1.dp, Color(0xFF333333), RoundedCornerShape(24.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${stats?.shadowEssence ?: 0}",
                            color = Color(0xFFF2F2F2),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color(0xFF7DD3FC), Color(0xFF0EA5E9))
                                    )
                                )
                        )
                    }
                }
            }

            // Quick Stats Panel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatMicroCard(
                    title = "ARENA ZAFERİ",
                    value = "${stats?.wins ?: 0}",
                    info = "Mağlubiyet: ${stats?.losses ?: 0}",
                    modifier = Modifier.weight(1f)
                )
                StatMicroCard(
                    title = "PARÇALANMA",
                    value = "${stats?.kills ?: 0}",
                    info = "Sıçrayan Kan: ${stats?.totalBloodShedCls ?: 0} ML",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selected Gladiator Showcase Header
            Text(
                text = "SEÇİLİ SAVAŞÇI",
                color = Color(0xFF880808),
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(6.dp))
            ActiveGladiatorCard(
                brawler = currentBrawler,
                level = activeBrawlerProg?.level ?: 1,
                activeSkin = activeBrawlerProg?.activeSkinId ?: "default"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Play 3D Beveled Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFAA0000), Color(0xFF660000))
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0x22FFFFFF),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { viewModel.startMatch(currentBrawler) },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Savaşa Gir",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SAVAŞA GİR",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        style = androidx.compose.ui.text.TextStyle(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Grid Layout Buttons to navigate
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HomeMenuOptionBtn(
                    title = "HEROLAR (15)",
                    subtitle = "Geliştir & Skin Seç",
                    icon = Icons.Default.Person,
                    color = Color(0xFF2C2C35),
                    accentColor = Color(0xFFFF003C),
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(GameViewModel.Screen.Wiki) }
                )
                HomeMenuOptionBtn(
                    title = "SHADOW MARKET",
                    subtitle = "Hero & Skin Aç",
                    icon = Icons.Default.ShoppingCart,
                    color = Color(0xFF2C2C35),
                    accentColor = Color(0xFFB300FF),
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(GameViewModel.Screen.Shop) }
                )
                HomeMenuOptionBtn(
                    title = "TASARIM KILAVUZU",
                    subtitle = "Sonsuz Harita & Lore",
                    icon = Icons.Default.Info,
                    color = Color(0xFF2C2C35),
                    accentColor = Color(0xFFFF9900),
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(GameViewModel.Screen.UnityGuide) }
                )
                HomeMenuOptionBtn(
                    title = "UNITY / ENGINE",
                    subtitle = "Geliştirici Önerisi",
                    icon = Icons.Default.Settings,
                    color = Color(0xFF2C2C35),
                    accentColor = Color(0xFF1DB954),
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(GameViewModel.Screen.UnityGuide) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Debug Cheats for testing/preview easily!
            OutlinedButton(
                onClick = { viewModel.debugGimmeMoney() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1DB954)),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF1DB954)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Cheat")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "HİLE PANELİ: +5000 ALTIN & +1000 SHADOW ESSENCE", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun CurrencyMeter(
    label: String,
    value: Int,
    icon: ImageVector,
    iconColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontSize = 8.sp, color = Color(0xFF8A8A93), fontWeight = FontWeight.Bold)
            Text(text = "$value", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun StatMicroCard(
    title: String,
    value: String,
    info: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161616)),
        border = BorderStroke(1.dp, Color(0xFF222222)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 9.sp,
                color = Color(0xFFFF0000),
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
                style = androidx.compose.ui.text.TextStyle(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = info,
                fontSize = 9.sp,
                color = Color(0xFFFF0000).copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun ActiveGladiatorCard(
    brawler: Brawler,
    level: Int,
    activeSkin: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161616)),
        border = BorderStroke(1.dp, Color(0xFF333333)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Rarity / Class Banner
            Text(
                text = "${brawler.rarity.name.uppercase()} • LEVEL $level",
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = Color(android.graphics.Color.parseColor(brawler.rarity.hexColor)),
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Big Bold Hero Name Accent
            Text(
                text = brawler.name.uppercase(),
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = (-2).sp,
                style = androidx.compose.ui.text.TextStyle(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                textAlign = TextAlign.Center
            )

            // Skin visual indicator
            Text(
                text = "SKIN: " + (brawler.skins.firstOrNull { it.id == activeSkin }?.name ?: "Varsayılan").uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF880808),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Three Column Quick Stats Layout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F0F0F))
                    .border(1.dp, Color(0xFF222222), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "HEALTH",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${brawler.hp}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "DAMAGE",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "${brawler.damage}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "SPEED",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "FAST",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFF0000)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeMenuOptionBtn(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(84.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161616)),
        border = BorderStroke(1.dp, Color(0xFF222222)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = accentColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = subtitle,
                    fontSize = 9.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
