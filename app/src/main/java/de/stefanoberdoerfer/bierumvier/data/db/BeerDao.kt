package de.stefanoberdoerfer.bierumvier.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(beers: List<BeerEntity>)

    @Query("SELECT * FROM BeerEntity WHERE id IS :id")
    fun getById(id: Long): Flow<BeerEntity>

    @Query("SELECT * FROM BeerEntity")
    fun getAllAsFlow(): Flow<List<BeerEntity>>

    @Query("SELECT MAX(id) FROM BeerEntity")
    suspend fun getHighestBeerId(): Long?

    @Query("SELECT COUNT(*) FROM BeerEntity")
    suspend fun countBeersInDb(): Long

    @Query("UPDATE BeerEntity SET evaluation = :value WHERE id IS :id")
    suspend fun updateEvaluation(id: Long, value: Float)
}