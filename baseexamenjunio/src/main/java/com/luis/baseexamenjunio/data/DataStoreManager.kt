package com.luis.baseexamenjunio.data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val VIEW_MODE = stringPreferencesKey("view_mode")
        val SORT_CRITERIA = stringPreferencesKey("sort_criteria")
    }

    suspend fun saveViewMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[VIEW_MODE] = mode
        }
    }

    val viewMode: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[VIEW_MODE] ?: "list"
        }

    suspend fun saveSortCriteria(criteria: String) {
        dataStore.edit { preferences ->
            preferences[SORT_CRITERIA] = criteria
        }
    }

    val sortCriteria: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[SORT_CRITERIA] ?: "price"
        }
}