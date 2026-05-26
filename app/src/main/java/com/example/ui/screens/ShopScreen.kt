package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
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

@Composable
fun ShopScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.playerStats.collectAsState()
    val unlockedBrawlers by viewModel.unlockedBrawlers.collectAsState()
    val unlockedSkins by viewModel.unlockedSkins.collectAsState()

    var activeTab by remember { mutableStateOf("Brawlers") } // "Brawlers" or "Skins"

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080707))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "SHADOW MARKET",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp,
                        style = androidx.compose.ui.text.TextStyle(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                    Text(
                        text = "Essence harcayarak yasaklı içerikleri aç",
                        fontSize = 11.sp,
                        color = Color(0xFFFF0000).copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                // Balance meters
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Gold", tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${stats?.gold ?: 0}", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = "Essence", tint = Color(0xFFB300FF), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${stats?.shadowEssence ?: 0}", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Black)
                    }
                }
            }

            // Tab Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF080707))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabSelectorBtn(
                    label = "SAVAŞÇILAR",
                    active = activeTab == "Brawlers",
                    modifier = Modifier.weight(1f),
                    onClick = { activeTab = "Brawlers" }
                )
                TabSelectorBtn(
                    label = "YASAKLI BUST / SKİNLER",
                    active = activeTab == "Skins",
                    modifier = Modifier.weight(1f),
                    onClick = { activeTab = "Skins" }
                )
            }

            // Shop grid contents
            if (activeTab == "Brawlers") {
                val lockedList = remember(viewModel.staticBrawlers, unlockedBrawlers) {
                    viewModel.staticBrawlers.map { brawler ->
                        val isUnlocked = unlockedBrawlers.any { it.brawlerId == brawler.id }
                        brawler to isUnlocked
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(lockedList) { (brawler, unlocked) ->
                        // Calculate unlock cost based on rarity
                        val unlockCost = when (brawler.rarity) {
                            Brawler.Rarity.COMMON -> 0
                            Brawler.Rarity.RARE -> 150
                            Brawler.Rarity.EPIC -> 300
                            Brawler.Rarity.LEGENDARY -> 500
                            Brawler.Rarity.DARK -> 800
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
                            border = BorderStroke(
                                1.dp,
                                if (unlocked) Color(0xFF1A1A22) else Color(android.graphics.Color.parseColor(brawler.rarity.hexColor))
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = brawler.name.substringBefore(" ("),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = brawler.rarity.displayName,
                                        fontSize = 8.sp,
                                        color = Color(android.graphics.Color.parseColor(brawler.rarity.hexColor)),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Hp: ${brawler.hp} Attack: ${brawler.damage}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF8A8A93)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                if (unlocked) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Saldırıya açık", tint = Color(0xFF1DB954), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = "KİLİT AÇIK", fontSize = 11.sp, color = Color(0xFF1DB954), fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            viewModel.buyBrawler(brawler.id, unlockCost)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB300FF)),
                                        enabled = (stats?.shadowEssence ?: 0) >= unlockCost,
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.fillMaxWidth(),
                                        contentPadding = PaddingValues(vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.Lock, contentDescription = "Buy", modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = "$unlockCost ESSENCE", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Skins buy catalog
                val skinsToBuy = remember(viewModel.staticBrawlers, unlockedSkins) {
                    val list = mutableListOf<Triple<String, String, com.example.data.model.BrawlerSkin>>() // skin, brawler name, object
                    viewModel.staticBrawlers.forEach { brawler ->
                        brawler.skins.filter { it.id != "${brawler.id}_default" }.forEach { skin ->
                            val isUnlocked = unlockedSkins.any { it.skinId == skin.id && it.unlocked }
                            list.add(Triple(brawler.name, brawler.id, skin))
                        }
                    }
                    list
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(skinsToBuy) { (brawlerName, brawlerId, skin) ->
                        val unlocked = unlockedSkins.any { it.skinId == skin.id && it.unlocked }
                        val characterOwn = unlockedBrawlers.any { it.brawlerId == brawlerId }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
                            border = BorderStroke(1.dp, if (unlocked) Color(0xFF1A1A22) else Color(0xFFFF5722))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = skin.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Sahibi: $brawlerName",
                                    fontSize = 9.sp,
                                    color = Color(0xFF8A8A93)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = skin.designDesc,
                                    fontSize = 10.sp,
                                    color = Color(0xFF6E6E77),
                                    minLines = 2
                                )

                                if (skin.hasUnratedNudityOrGore) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "KANLI GORE SEVİYE (+18)",
                                        fontSize = 8.sp,
                                        color = Color(0xFFFF003C),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(Color(0x33FF003C))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (unlocked) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Bought", tint = Color(0xFF1DB954), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = "SATIN ALINDI", fontSize = 11.sp, color = Color(0xFF1DB954), fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            viewModel.buySkin(skin.id, brawlerId, skin.shadowEssenceCost)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                                        enabled = (stats?.shadowEssence ?: 0) >= skin.shadowEssenceCost && characterOwn,
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.fillMaxWidth(),
                                        contentPadding = PaddingValues(vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.Lock, contentDescription = "Buy", modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (characterOwn) "${skin.shadowEssenceCost} ESSENCE" else "HERO KİLİTLİ",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabSelectorBtn(
    label: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (active) {
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFAA0000), Color(0xFF660000))
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF161616), Color(0xFF161616))
                    )
                }
            )
            .border(
                1.dp,
                if (active) Color(0xFFFF0000).copy(alpha = 0.4f) else Color(0xFF222222),
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = if (active) Color.White else Color.Gray,
            letterSpacing = 1.sp,
            style = androidx.compose.ui.text.TextStyle(
                fontStyle = if (active) androidx.compose.ui.text.font.FontStyle.Italic else androidx.compose.ui.text.font.FontStyle.Normal
            )
        )
    }
}
