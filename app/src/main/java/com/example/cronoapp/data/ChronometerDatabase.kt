package com.example.cronoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cronoapp.data.dao.ChronometerDao
import com.example.cronoapp.data.entities.Chronometer

@Database(
    entities = [Chronometer::class],
    version = 1,
    exportSchema = false
)
abstract class ChronometerDatabase: RoomDatabase() {
    abstract fun chronometerDao(): ChronometerDao
}