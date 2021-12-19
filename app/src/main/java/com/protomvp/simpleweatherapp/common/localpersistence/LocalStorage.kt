package com.protomvp.simpleweatherapp.common.localpersistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LocalStorage constructor(private val context: Context) {

    suspend fun persist(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[prefKey] = value
        }
    }

    suspend fun getKey(key: String): String {
        val prefKey = stringPreferencesKey(key)
        val flow: Flow<String> = context.dataStore.data
            .map { preferences ->
                preferences[prefKey] ?: ""
            }
        return flow.first()
    }
}