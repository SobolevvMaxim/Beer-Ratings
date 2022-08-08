package com.sobolev.beerratings.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "chosen_region_store"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = STORE_NAME
)

class RegionStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : RegionStore {

    private val _dataStore = context.dataStore

    override fun getRegion(): Flow<String> =
        _dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[PreferencesKeys.CHOSEN_REGION_KEY] ?: ""
        }


    override suspend fun changeRegion(newChosenRegion: String) {
        _dataStore.edit {
            it[PreferencesKeys.CHOSEN_REGION_KEY] = newChosenRegion
        }
    }

    private object PreferencesKeys {
        val CHOSEN_REGION_KEY = stringPreferencesKey("region_prefs")
    }
}

interface RegionStore {
    fun getRegion() :Flow<String>

    suspend fun changeRegion(newChosenRegion: String)
}