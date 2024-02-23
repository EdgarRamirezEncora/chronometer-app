package com.example.cronoapp.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStore(private val context: Context) {
    companion object {
        private val Context.datastore : DataStore<Preferences> by preferencesDataStore("chronometerAppPreferences")
        val FIRST_TIME = booleanPreferencesKey("first_time")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    val getFirstTime: Flow<Boolean> = context.datastore.data
        .map { it[FIRST_TIME] ?: true }

    suspend fun updateFirstTime(value: Boolean) {
        context.datastore.edit { it[FIRST_TIME] = value }
    }

    val getDarkMode: Flow<Boolean> = context.datastore.data
        .map { it[DARK_MODE] ?: false }

    suspend fun updateDarkMode(value: Boolean) {
        context.datastore.edit { it[DARK_MODE] = value }
    }
}