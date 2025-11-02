package com.kaninitech.salesnote.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for DataStore
private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user-business_prefs")

class BusinessDetPrefs(private val context: Context) {

    companion object {
        val IS_BUSINESS_DATA_SET = booleanPreferencesKey("is_business_data_set")
        val BUSINESS_NAME = stringPreferencesKey("business_name")
        val BUSINESS_ADDRESS = stringPreferencesKey("business_address")
        val BUSINESS_ID = stringPreferencesKey("business_id")
    }

    // Save business data
    suspend fun saveUserBizPref(
        name: String,
        address: String,
        businessId: String
    ) {
        context.userDataStore.edit { preferences ->
            preferences[IS_BUSINESS_DATA_SET] = true
            preferences[BUSINESS_NAME] = name
            preferences[BUSINESS_ADDRESS] = address
            preferences[BUSINESS_ID] = businessId
        }
    }

    // Observe business data (Flow)
    val userBizPrefData: Flow<UserBusinessData> = context.userDataStore.data.map { preferences ->
        UserBusinessData(
            isDataSet = preferences[IS_BUSINESS_DATA_SET] ?: false,
            businessName = preferences[BUSINESS_NAME] ?: "",
            businessAddress = preferences[BUSINESS_ADDRESS] ?: "",
            businessId = preferences[BUSINESS_ID] ?: ""
        )
    }

    // Clear all data
    suspend fun clearUserData() {
        context.userDataStore.edit { it.clear() }
    }
}

// Data class for structured access
data class UserBusinessData(
    val isDataSet: Boolean,
    val businessName: String,
    val businessAddress: String,
    val businessId: String
)
