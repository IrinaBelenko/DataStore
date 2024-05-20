package com.ukropsoft.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ukropsoft.datastore.ui.theme.Red
import kotlinx.coroutines.flow.map

private val Context.protoDataStore by dataStore("settings.json", SettingsSerializer)

class ProtoDataStoreManager(val context: Context) {

    suspend fun saveColor(color: ULong) {
        context.protoDataStore.updateData { data ->
            data.copy(bgColor = color)
        }
    }

    suspend fun saveTextSize(size: Int) {
        context.protoDataStore.updateData { data ->
            data.copy(textSize = size)
        }
    }

    suspend fun saveSettings(settingsData: SettingsData) {
        context.protoDataStore.updateData {
            settingsData
        }
    }

    fun getSettings() = context.protoDataStore.data

}