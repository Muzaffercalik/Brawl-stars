package com.example.data.repository

import com.example.data.database.GameDao
import com.example.data.model.Brawler
import com.example.data.model.BrawlersData
import com.example.data.model.MapConcept
import com.example.data.model.PlayerStats
import com.example.data.model.UnlockedBrawler
import com.example.data.model.UnlockedSkin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class GameRepository(private val gameDao: GameDao) {

    val allStaticBrawlers: List<Brawler> = BrawlersData.brawlers
    val allStaticMaps: List<MapConcept> = BrawlersData.maps

    val playerStats: Flow<PlayerStats?> = gameDao.getPlayerStats()
    val unlockedBrawlers: Flow<List<UnlockedBrawler>> = gameDao.getUnlockedBrawlers()
    val unlockedSkins: Flow<List<UnlockedSkin>> = gameDao.getUnlockedSkins()

    // Initialize default values if database is empty
    suspend fun initializeDatabaseIfNeeded() {
        val stats = gameDao.getPlayerStatsSync()
        if (stats == null) {
            // Seed base player stats
            gameDao.insertPlayerStats(
                PlayerStats(
                    gold = 1500,
                    shadowEssence = 250,
                    chosenBrawlerId = "kassap",
                    wins = 0,
                    losses = 0,
                    kills = 0,
                    totalBloodShedCls = 0,
                    forbiddenModeUnlocked = false
                )
            )

            // Seed preloaded unlocked brawlers: Kassap, Kala, Ignis (Common tier start!)
            gameDao.insertUnlockedBrawler(UnlockedBrawler(brawlerId = "kassap", unlocked = true, level = 1, activeSkinId = "default"))
            gameDao.insertUnlockedBrawler(UnlockedBrawler(brawlerId = "kala", unlocked = true, level = 1, activeSkinId = "default"))
            gameDao.insertUnlockedBrawler(UnlockedBrawler(brawlerId = "ignis", unlocked = true, level = 1, activeSkinId = "default"))

            // Seed default skins unlocked
            gameDao.insertUnlockedSkin(UnlockedSkin("kassap_default", "kassap", true))
            gameDao.insertUnlockedSkin(UnlockedSkin("kala_default", "kala", true))
            gameDao.insertUnlockedSkin(UnlockedSkin("ignis_default", "ignis", true))
        }
    }

    suspend fun chooseBrawler(brawlerId: String) {
        gameDao.updateChosenBrawler(brawlerId)
    }

    suspend fun buyBrawler(brawlerId: String, cost: Int): Boolean {
        val currentStats = gameDao.getPlayerStatsSync() ?: return false
        if (currentStats.shadowEssence >= cost) {
            val updatedEssence = currentStats.shadowEssence - cost
            gameDao.updateCurrencies(currentStats.gold, updatedEssence)
            gameDao.insertUnlockedBrawler(UnlockedBrawler(brawlerId = brawlerId, unlocked = true, level = 1, activeSkinId = "default"))
            gameDao.insertUnlockedSkin(UnlockedSkin("${brawlerId}_default", brawlerId, true))
            return true
        }
        return false
    }

    suspend fun buySkin(skinId: String, brawlerId: String, cost: Int): Boolean {
        val currentStats = gameDao.getPlayerStatsSync() ?: return false
        if (currentStats.shadowEssence >= cost) {
            val updatedEssence = currentStats.shadowEssence - cost
            gameDao.updateCurrencies(currentStats.gold, updatedEssence)
            gameDao.insertUnlockedSkin(UnlockedSkin(skinId = skinId, brawlerId = brawlerId, unlocked = true))
            return true
        }
        return false
    }

    suspend fun equipSkin(brawlerId: String, skinId: String) {
        val unlockedList = gameDao.getUnlockedBrawlers().firstOrNull() ?: emptyList()
        val match = unlockedList.firstOrNull { it.brawlerId == brawlerId }
        if (match != null) {
            gameDao.insertUnlockedBrawler(match.copy(activeSkinId = skinId))
        }
    }

    suspend fun upgradeBrawler(brawlerId: String, costGold: Int): Boolean {
        val currentStats = gameDao.getPlayerStatsSync() ?: return false
        val brawlers = gameDao.getUnlockedBrawlers().firstOrNull() ?: return false
        val target = brawlers.firstOrNull { it.brawlerId == brawlerId } ?: return false

        if (currentStats.gold >= costGold) {
            val updatedGold = currentStats.gold - costGold
            gameDao.updateCurrencies(updatedGold, currentStats.shadowEssence)
            gameDao.insertUnlockedBrawler(target.copy(level = target.level + 1))
            return true
        }
        return false
    }

    suspend fun unlockForbiddenMode(costEssence: Int): Boolean {
        val currentStats = gameDao.getPlayerStatsSync() ?: return false
        if (currentStats.shadowEssence >= costEssence) {
            val updatedEssence = currentStats.shadowEssence - costEssence
            gameDao.updateCurrencies(currentStats.gold, updatedEssence)
            gameDao.updateForbiddenMode(true)
            return true
        }
        return false
    }

    suspend fun completeMatch(win: Boolean, kills: Int, bloodShed: Int) {
        val goldReward = if (win) 150 else 50
        val essenceReward = if (win) 20 else 5 + (kills * 2)

        val stats = gameDao.getPlayerStatsSync() ?: return
        val newGold = stats.gold + goldReward
        val newEssence = stats.shadowEssence + essenceReward

        gameDao.updateCurrencies(newGold, newEssence)
        gameDao.incrementStats(
            winCount = if (win) 1 else 0,
            lossCount = if (win) 0 else 1,
            killCount = kills,
            bloodCount = bloodShed
        )
    }

    // Cheat to get currencies easily for debugging/preview and immediate unlocks!
    suspend fun debugAddCurrencies() {
        val stats = gameDao.getPlayerStatsSync() ?: return
        gameDao.updateCurrencies(stats.gold + 5000, stats.shadowEssence + 1000)
    }
}
