package com.example.cronoapp.domain.repositories

import com.example.cronoapp.data.dao.ChronometerDao
import com.example.cronoapp.data.entities.Chronometer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChronometerRepository @Inject constructor(
    private val chronometerDao: ChronometerDao
) {
    suspend fun addChronometer(chronometer: Chronometer) = chronometerDao
                                                                    .insertChronometer(chronometer)

    suspend fun updateChronometer(chronometer: Chronometer) = chronometerDao
                                                                    .updateChronometer(chronometer)

    suspend fun deleteChronometer(chronometer: Chronometer) = chronometerDao
                                                                    .deleteChronometer(chronometer)

    fun getAllChronometers(): Flow<List<Chronometer>> = chronometerDao
                                                                .getAllChronometers()
                                                                .flowOn(Dispatchers.IO)
                                                                .conflate()

    fun getChronometer(id: Long): Flow<Chronometer> = chronometerDao
                                                            .getChronometer(id)
                                                            .flowOn(Dispatchers.IO)
                                                            .conflate()

    fun getChronometersByTitle(title: String): Flow<List<Chronometer>> = chronometerDao
                                                                    .getChronometersByTitle(title)
                                                                    .flowOn(Dispatchers.IO)
                                                                    .conflate()
}