package com.example.propertymanagement.SFServices

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.propertymanagement.SFServices.model.SFUserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PManagerSFRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        private val USER_ID = intPreferencesKey("userId")
        private val USER_EMAIL = stringPreferencesKey("userEmail")
        private val USER_PHONE_NUMBER = stringPreferencesKey("userPhoneNumber")
        private val USER_FIRST_NAME = stringPreferencesKey("userFirstName")
        private val USER_LAST_NAME = stringPreferencesKey("userLastName")
        private val TOKEN = stringPreferencesKey("token")
    }

    suspend fun saveUserDetails(
        sfUserDetails: SFUserDetails
    ) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = sfUserDetails.userId!!
            preferences[USER_EMAIL] = sfUserDetails.userEmail
            preferences[USER_PHONE_NUMBER] = sfUserDetails.userPhoneNumber
            preferences[USER_FIRST_NAME] = sfUserDetails.userFirstName
            preferences[USER_LAST_NAME] = sfUserDetails.userLastName
            preferences[TOKEN] = sfUserDetails.token
        }
    }

    val sfUserId: Flow<Int?> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[USER_ID] ?: null
        }

    val sfUserFirstName: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[USER_FIRST_NAME] ?: ""
        }

    val sfUserLastName: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[USER_LAST_NAME] ?: ""
        }

    val sfUserPhoneNumber: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[USER_PHONE_NUMBER] ?: ""
        }

    val sfUserEmail: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[USER_EMAIL] ?: ""
        }

    val sfUserToken: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[TOKEN] ?: ""
        }

    suspend fun deleteAllPreferences() {
        dataStore.edit {
            it.clear()
        }
    }


    val userDetails: Flow<SFUserDetails> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it.toSFUserDetails(
                userId = it[USER_ID] ?: null,
                userEmail = it[USER_EMAIL] ?: "",
                userPhoneNumber = it[USER_PHONE_NUMBER] ?: "",
                userFirstName = it[USER_FIRST_NAME] ?: "",
                userLastName = it[USER_LAST_NAME] ?: "",
                token = it[TOKEN] ?: ""
            )
        }

    private fun Preferences.toSFUserDetails(
        userId: Int?,
        userEmail: String,
        userPhoneNumber: String,
        userFirstName: String,
        userLastName: String,
        token: String
    ): SFUserDetails = SFUserDetails(
        userId = userId,
        userEmail = userEmail,
        userPhoneNumber = userPhoneNumber,
        userFirstName = userFirstName,
        userLastName = userLastName,
        token = token

    )
}