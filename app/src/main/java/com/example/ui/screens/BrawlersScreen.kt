package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Brawler
import com.example.ui.viewmodel.GameViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrawlersScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val brawlersList by viewModel.unlockedBrawlers.collectAsState()
    val skinsList by viewModel.unlockedSkins.collectAsState()
    val stats by viewModel.playerStats.collectAsState()

    var selectedBrawler by remember { mutableStateOf<Brawler?>(null) }

    // Automatically set initially selected brawler
    LaunchedEffect(viewModel.staticBrawlers) {
        if (selectedBrawler == null) {
            selectedBrawler = viewModel.staticBrawlers.first()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080707))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0C0C0C))
                    .border(BorderStroke(1.dp, Color(0xFF222222)))
                    .statusBarsPadding()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateTo(GameViewModel.Screen.MainMenu) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "SAVAŞÇI CEPHANESİ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp,
                        style = androidx.compose.ui.text.TextStyle(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                    Text(
                        text = "Seçkin savaşçılarını geliştir ve giydir",
                        fontSize = 11.sp,
                        color = Color(0xFFFF0000).copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Row(modifier = Modifier.weight(1f)) {
                // Left Column: List of 15 Brawlers
                LazyColumn(
                    modifier = Modifier
                        .width(135.dp)
                        .fillMaxHeight()
                        .background(Color(0xFF0C0C0C))
                        .border(BorderStroke(1.dp, Color(0xFF222222)))
                ) {
                    items(viewModel.staticBrawlers) { brawler ->
                        val unlockedState = brawlersList.firstOrNull { it.brawlerId == brawler.id }
                        val isEquipped = stats?.chosenBrawlerId == brawler.id
                        val isSelected = selectedBrawler?.id == brawler.id

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedBrawler = brawler }
                                .background(
                                    if (isSelected) Color(0xFF2C1014) else Color.Transparent
                                )
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = brawler.name.substringBefore(" "),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (unlockedState != null) Color.White else Color(0x66FFFFFF)
                                    )
                                    if (unlockedState == null) {
                                        Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color(0x66C70039), modifier = Modifier.size(12.dp))
                                    } else if (isEquipped) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Equipped", tint = Color(0xFF1DB954), modifier = Modifier.size(12.dp))
                                    }
                                }
                                Text(
                                    text = brawler.rarity.name,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(android.graphics.Color.parseColor(brawler.rarity.hexColor))
                                )
                                if (unlockedState != null) {
                                    Text(
                                        text = "SEV ${unlockedState.level}",
                                        fontSize = 8.sp,
                                        color = Color(0xFFFFD700)
                                    )
                                }
                            }
                        }
                        Divider(color = Color(0xFF181820))
                    }
                }

                // Right Column: Detailed View of chosen brawler
                selectedBrawler?.let { b ->
                    val unlockProg = brawlersList.firstOrNull { it.brawlerId == b.id }
                    val activeSkin = unlockProg?.activeSkinId ?: "default"
                    val isEquipped = stats?.chosenBrawlerId == b.id

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Quick Profile HeaderCard
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
                                border = BorderStroke(1.dp, Color(0xFF2C2C35))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = b.name,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = b.rarity.name,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color(android.graphics.Color.parseColor(b.rarity.hexColor)),
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color(android.graphics.Color.parseColor(b.rarity.hexColor)),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Status info
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(text = "SAĞLIK (HP)", fontSize = 9.sp, color = Color(0xFF8A8A93))
                                            Text(text = "${b.hp}", fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color(0xFF1DB954))
                                        }
                                        Column {
                                            Text(text = "HASAR", fontSize = 9.sp, color = Color(0xFF8A8A93))
                                            Text(text = "${b.damage}", fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color(0xFFFF003C))
                                        }
                                        Column {
                                            Text(text = "HIZ ORANI", fontSize = 9.sp, color = Color(0xFF8A8A93))
                                            Text(text = "x${b.speed}", fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color(0xFFFF9900))
                                        }
                                    }
                                }
                            }
                        }

                        // Equip of unlock options
                        item {
                            if (unlockProg != null) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    if (isEquipped) {
                                        Button(
                                            onClick = {},
                                            enabled = false,
                                            colors = ButtonDefaults.buttonColors(disabledContainerColor = Color(0xFF1DB954).copy(alpha = 0.2f)),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Default.Check, contentDescription = "Active", tint = Color(0xFF1DB954))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(text = "SAVAŞÇIN HAZIR", color = Color(0xFF1DB954))
                                        }
                                    } else {
                                        Button(
                                            onClick = { viewModel.selectBrawler(b.id) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF003C)),
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(text = "SAVAŞÇI SEÇ", color = Color.White, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    // Upgrade Button
                                    val upgradeCostGold = unlockProg.level * 400
                                    Button(
                                        onClick = {
                                            viewModel.upgradeBrawler(b.id, upgradeCostGold)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
                                        enabled = (stats?.gold ?: 0) >= upgradeCostGold,
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "SEVİYE YÜKSELT", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                            Text(text = "$upgradeCostGold GOLD SOUL", fontSize = 9.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            } else {
                                // Locked state warning
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF220A0E)),
                                    border = BorderStroke(1.dp, Color(0xFF900C3F))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "BU KARANLIK GLADYATÖR KİLİTLİ!",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFF003C)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Shadow Market'ten Shadow Essence karşılığı kilidi açabilirsin.",
                                            fontSize = 10.sp,
                                            color = Color(0xFF8A8A93),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = { viewModel.navigateTo(GameViewModel.Screen.Shop) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB300FF)),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(text = "MARKETE GİT", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }

                        // Backstory info (brutal lore)
                        item {
                            Text(text = "HİKAYESİ (LORE)", fontSize = 11.sp, color = Color(0xFFFF003C), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = b.backstory,
                                fontSize = 12.sp,
                                color = Color(0xFFE6E6EB),
                                lineHeight = 18.sp
                            )
                        }

                        // Attack, Super & Ultimate details
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(text = "YETENEK DETAILS", fontSize = 11.sp, color = Color(0xFFFF003C), fontWeight = FontWeight.Bold)

                                AbilityRow(
                                    title = b.attackInfo.substringBefore(":"),
                                    desc = b.attackInfo.substringAfter(":"),
                                    type = "Normal Vuruş",
                                    badgeColor = Color(0xFFFF5722)
                                )
                                AbilityRow(
                                    title = b.superName,
                                    desc = b.superDesc,
                                    type = "Süper Yetenek",
                                    badgeColor = Color(0xFFB300FF)
                                )
                                AbilityRow(
                                    title = b.ultimateName,
                                    desc = b.ultimateDesc,
                                    type = "Forbidden Ultimate",
                                    badgeColor = Color(0xFFFF003C)
                                )
                            }
                        }

                        // Audio specs
                        item {
                            Text(text = "SES DOSYALARI & ÇIĞLIKLAR", fontSize = 11.sp, color = Color(0xFFFF003C), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            b.sounds.forEach { clip ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Audio clip", tint = Color(0xFF8A8A93), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = clip, fontSize = 11.sp, color = Color(0xFF8A8A93))
                                }
                            }
                        }

                        // Skin wardrobe manager
                        item {
                            Text(text = "SKINLERİ VE KIYAFETLER", fontSize = 11.sp, color = Color(0xFFFF003C), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(b.skins) { skin ->
                                    val skinUnlocked = skinsList.any { it.skinId == skin.id && it.unlocked }
                                    val isSkinEquipped = activeSkin == skin.id

                                    Card(
                                        modifier = Modifier
                                            .width(130.dp)
                                            .clickable(enabled = skinUnlocked) {
                                                viewModel.equipSkin(b.id, skin.id)
                                            },
                                        colors = CardDefaults.cardColors(containerColor = if (isSkinEquipped) Color(0xFF2C1014) else Color(0xFF131317)),
                                        border = BorderStroke(1.dp, if (isSkinEquipped) Color(0xFFFF003C) else Color(0xFF2C2C35))
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(text = skin.name, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(text = skin.designDesc, fontSize = 9.sp, color = Color(0xFF8A8A93), minLines = 2)
                                            Spacer(modifier = Modifier.height(8.dp))

                                            if (skin.hasUnratedNudityOrGore) {
                                                Text(
                                                    text = "GORE (+18)",
                                                    fontSize = 8.sp,
                                                    color = Color(0xFFFF003C),
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(2.dp))
                                                        .background(Color(0x33FF003C))
                                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                                Spacer(modifier = Modifier.height(6.dp))
                                            }

                                            if (skinUnlocked) {
                                                if (isSkinEquipped) {
                                                    Text(text = "KUŞANILDI", fontSize = 9.sp, color = Color(0xFF1DB954), fontWeight = FontWeight.Bold)
                                                } else {
                                                    Text(text = "SEÇMEK İÇİN TIKLA", fontSize = 9.sp, color = Color(0xFF8A8A93))
                                                }
                                            } else {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color(0xFFB300FF), modifier = Modifier.size(10.dp))
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(text = "${skin.shadowEssenceCost} ESSENCE", fontSize = 9.sp, color = Color(0xFFB300FF), fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Margin safety spacer
                        item {
                            Spacer(modifier = Modifier.height(48.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AbilityRow(
    title: String,
    desc: String,
    type: String,
    badgeColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
        border = BorderStroke(1.dp, Color(0xFF1E1E24))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(
                    text = type,
                    fontSize = 8.sp,
                    color = badgeColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = desc, fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 16.sp)
        }
    }
}
