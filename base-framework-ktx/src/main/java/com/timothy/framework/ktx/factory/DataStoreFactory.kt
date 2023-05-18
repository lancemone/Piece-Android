package com.timothy.framework.ktx.factory

import android.content.Context
import android.util.Log
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.timothy.framework.ktx.FWKtxApplication
import com.timothy.framework.ktx.base.DataStoreBasePreferences
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ConcurrentHashMap

object DataStoreFactory {
    private const val DATA_STORE_TAG = "DataStore"
    private const val USER_PREFERENCES = "default_user_preferences"
    private lateinit var defaultDataStore: DataStoreBasePreferences
    private val dataStoreMaps = ConcurrentHashMap<String, DataStoreBasePreferences>()

    private val applicationScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            Log.e(
                DATA_STORE_TAG,
                "applicationScope:\n${throwable.message.toString()}", throwable
            )
        })

    fun getDefaultPreferences(): DataStoreBasePreferences{
        if (this::defaultDataStore.isInitialized.not()) {
            defaultDataStore = createPreferencesDataStore(USER_PREFERENCES)
        }
        return defaultDataStore
    }

    fun getPreferencesDataStore(name: String) =
        createPreferencesDataStore(name)

    private fun initDefaultPreferencesDataStore() {
        if (this::defaultDataStore.isInitialized.not()) {
            defaultDataStore = createPreferencesDataStore(USER_PREFERENCES)
        }
    }

    private fun createPreferencesDataStore(name: String): DataStoreBasePreferences {
        if (dataStoreMaps.containsKey(name)) {
            return dataStoreMaps[name]!!
        }
        val preferences = DataStoreBasePreferences(
            PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                migrations = listOf(
                    SharedPreferencesMigration(
                        FWKtxApplication.application,
                        name
                    )
                ),
                applicationScope,
                produceFile = { FWKtxApplication.application.preferencesDataStoreFile(name) }
            )
        )
        dataStoreMaps[name] = preferences
        return preferences
    }
}