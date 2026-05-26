package com.example.data.database

import android.content.Context
import androidx.room.*
import com.example.data.model.PlayerStats
import com.example.data.model.UnlockedBrawler
import com.example.data.model.UnlockedSkin
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM player_stats WHERE id = 1")
    fun getPlayerStats(): Flow<PlayerStats?>

    @Query("SELECT * FROM player_stats WHERE id = 1")
    suspend fun getPlayerStatsSync(): PlayerStats?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerStats(stats: PlayerStats)

    @Query("SELECT * FROM unlocked_brawler")
    fun getUnlockedBrawlers(): Flow<List<UnlockedBrawler>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnlockedBrawler(brawler: UnlockedBrawler)

    @Query("SELECT * FROM unlocked_skin")
    fun getUnlockedSkins(): Flow<List<UnlockedSkin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnlockedSkin(skin: UnlockedSkin)

    @Query("UPDATE player_stats SET gold = :newGold, shadowEssence = :newEssence WHERE id = 1")
    suspend fun updateCurrencies(newGold: Int, newEssence: Int)

    @Query("UPDATE player_stats SET chosenBrawlerId = :brawlerId WHERE id = 1")
    suspend fun updateChosenBrawler(brawlerId: String)

    @Query("UPDATE player_stats SET forbiddenModeUnlocked = :unlocked WHERE id = 1")
    suspend fun updateForbiddenMode(unlocked: Boolean)

    @Query("UPDATE player_stats SET wins = wins + :winCount, losses = losses + :lossCount, kills = kills + :killCount, totalBloodShedCls = totalBloodShedCls + :bloodCount WHERE id = 1")
    suspend fun incrementStats(winCount: Int, lossCount: Int, killCount: Int, bloodCount: Int)
}

@Database(entities = [PlayerStats::class, UnlockedBrawler::class, UnlockedSkin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shadow_brawl_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
