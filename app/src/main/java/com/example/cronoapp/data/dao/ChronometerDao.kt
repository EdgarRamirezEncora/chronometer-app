package com.example.cronoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cronoapp.data.entities.Chronometer
import kotlinx.coroutines.flow.Flow

@Dao
interface ChronometerDao {

    @Query("SELECT * FROM chronometers")
    fun getAllChronometers(): Flow<List<Chronometer>>

    @Query("SELECT * FROM chronometers WHERE id= :id")
    fun getChronometer(id: Long): Flow<Chronometer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChronometer(chronometer: Chronometer)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChronometer(chronometer: Chronometer)

    @Delete
    suspend fun deleteChronometer(chronometer: Chronometer)
}