package com.timothy.framework.ktx.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.framework.ktx.base.DataStoreBasePreferences
 * @Author: MoTao
 * @Date: 2023-05-09
 * <p>
 * <p/>
 */
class DataStoreBasePreferences(private val dataSore: DataStore<Preferences>) {
    private val dataSoreData = dataSore.data

    companion object{
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO);
    }

    fun putString(key: String, value: String?) = putData(stringPreferencesKey(key), value)

    fun putStringSet(key: String, values: Set<String>?) =
        putData(stringSetPreferencesKey(key), values)


    fun putInt(key: String, value: Int) = putData(intPreferencesKey(key), value)

    fun putLong(key: String, value: Long) = putData(longPreferencesKey(key), value)

    fun putFloat(key: String, value: Float) = putData(floatPreferencesKey(key), value)

    fun putBoolean(key: String, value: Boolean) = putData(booleanPreferencesKey(key), value)

    fun getString(key: String, defValue: String?=null): String? =
        readNullableData(stringPreferencesKey(key), defValue)

    fun getStringSet(key: String, defValues: Set<String>?): Set<String>? =
        readNullableData(stringSetPreferencesKey(key), defValues)

    fun getInt(key: String, defValue: Int): Int =
        readNonNullData(intPreferencesKey(key), defValue)

    fun getLong(key: String, defValue: Long): Long =
        readNonNullData(longPreferencesKey(key), defValue)

    fun getFloat(key: String, defValue: Float): Float =
        readNonNullData(floatPreferencesKey(key), defValue)

    fun getBoolean(key: String, defValue: Boolean): Boolean =
        readNonNullData(booleanPreferencesKey(key), defValue)

    suspend fun getStringFlow(key: String, defValue: String): Flow<String> =
        readNonNullFlowData(stringPreferencesKey(key), defValue)

    suspend fun getStringSetFlow(key: String, defValues: Set<String>): Flow<Set<String>> =
        readNonNullFlowData(stringSetPreferencesKey(key), defValues)

    suspend fun getIntFlow(key: String, defValue: Int): Flow<Int> =
        readNonNullFlowData(intPreferencesKey(key), defValue)

    suspend fun getLongFlow(key: String, defValue: Long): Flow<Long> =
        readNonNullFlowData(longPreferencesKey(key), defValue)

    suspend fun getFloatFlow(key: String, defValue: Float): Flow<Float> =
        readNonNullFlowData(floatPreferencesKey(key), defValue)

    suspend fun getBooleanFlow(key: String, defValue: Boolean): Flow<Boolean> =
        readNonNullFlowData(booleanPreferencesKey(key), defValue)

    fun getStringBlock(key: String, defValue: String?, block: (String?) -> Unit) =
        readEmitNullableData(key = stringPreferencesKey(key), defValue = defValue, block = block)

    fun getStringSetBlock(key: String, defValues: Set<String>?, block: (Set<String>?) -> Unit) =
        readEmitNullableData(stringSetPreferencesKey(key), defValues, block)

    fun getIntBlock(key: String, defValue: Int, block: (Int) -> Unit) =
        readEmitData(intPreferencesKey(key), defValue, block)

    fun getLongBlock(key: String, defValue: Long, block: (Long) -> Unit) =
        readEmitData(longPreferencesKey(key), defValue, block)

    fun getFloatBlock(key: String, defValue: Float, block: (Float) -> Unit) =
        readEmitData(floatPreferencesKey(key), defValue, block)

    fun getBooleanBlock(key: String, defValue: Boolean, block: (Boolean) -> Unit) =
        readEmitData(booleanPreferencesKey(key), defValue, block)

    private fun <T> putData(key: Preferences.Key<T>, value:T?){
        scope.launch {
            dataSore.edit {
                if (value == null) it.remove(key) else it[key] = value
            }
        }
    }

    private suspend fun <T> readNonNullFlowData(key: Preferences.Key<T>, defValue: T): Flow<T>{
        return dataSoreData.catch {
            if (it is IOException){
                it.printStackTrace()
                emit(emptyPreferences())
            }else{
                throw it
            }
        }.map {
            it[key] ?: defValue
        }
    }

    private suspend fun <T> readNullableFlowData(key: Preferences.Key<T>, defValue: T?): Flow<T?>{
        return dataSoreData.catch {
            if (it is IOException){
                it.printStackTrace()
                emit(emptyPreferences())
            }else{
                throw it
            }
        }.map {
            it[key] ?: defValue
        }
    }

    private fun <T> readNonNullData(key: Preferences.Key<T>, defValue: T) : T{
        return runBlocking {
            dataSoreData.map {
                it[key] ?: defValue
            }.first()
        }
    }

    private fun <T> readNullableData(key: Preferences.Key<T>, defValue: T?) : T?{
        return runBlocking {
            dataSoreData.map {
                it[key] ?: defValue
            }.firstOrNull()
        }
    }

    private fun <T> readEmitData(key: Preferences.Key<T>, defValue: T, block: (T) -> Unit){
        scope.launch {
            readNonNullFlowData(key, defValue).collectLatest {
                block(it)
            }
        }
    }

    private fun <T> readEmitNullableData(key: Preferences.Key<T>, defValue: T?, block: (T?) -> Unit){
        scope.launch {
            readNullableFlowData(key, defValue).collectLatest {
                block(it)
            }
        }
    }
}