package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Data model representing high-fidelity Brawlers as requested by the user
data class Brawler(
    val id: String,
    val name: String,
    val rarity: Rarity,
    val backstory: String,
    val hp: Int,
    val damage: Int,
    val speed: Float, // speed multiplier (e.g., 1.0f is normal)
    val attackInfo: String,
    val superName: String,
    val superDesc: String,
    val ultimateName: String,
    val ultimateDesc: String,
    val sounds: List<String>,
    val animations: List<String>,
    val skins: List<BrawlerSkin>
) {
    enum class Rarity(val displayName: String, val hexColor: String) {
        COMMON("Common", "#8A8A93"),
        RARE("Rare", "#1D89F5"),
        EPIC("Epic", "#B300FF"),
        LEGENDARY("Legendary", "#EFF300"),
        DARK("Dark", "#E00034")
    }
}

data class BrawlerSkin(
    val id: String,
    val name: String,
    val shadowEssenceCost: Int,
    val hasUnratedNudityOrGore: Boolean,
    val designDesc: String
)

// Map concept data structures
data class MapConcept(
    val title: String,
    val lore: String,
    val dangerZones: String,
    val primaryColorHex: String,
    val visualPrompt: String
)

// Room Entities for persistence of progress
@Entity(tableName = "player_stats")
data class PlayerStats(
    @PrimaryKey val id: Int = 1,
    val gold: Int = 2000,
    val shadowEssence: Int = 500,
    val chosenBrawlerId: String = "kassap",
    val wins: Int = 0,
    val losses: Int = 0,
    val kills: Int = 0,
    val totalBloodShedCls: Int = 0,
    val forbiddenModeUnlocked: Boolean = false
)

@Entity(tableName = "unlocked_brawler")
data class UnlockedBrawler(
    @PrimaryKey val brawlerId: String,
    val unlocked: Boolean = false,
    val level: Int = 1,
    val activeSkinId: String = "default"
)

@Entity(tableName = "unlocked_skin")
data class UnlockedSkin(
    @PrimaryKey val skinId: String,
    val brawlerId: String,
    val unlocked: Boolean = false
)
