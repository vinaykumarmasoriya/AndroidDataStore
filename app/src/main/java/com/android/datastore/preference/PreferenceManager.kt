package com.android.datastore.preference

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import com.android.datastore.preference.PreferencesKeys.AGE
import com.android.datastore.preference.PreferencesKeys.IS_ACCOUNT_ACTIVE
import com.android.datastore.preference.PreferencesKeys.LOCATION
import com.android.datastore.preference.PreferencesKeys.PREFERENCE_NAME
import com.android.datastore.preference.PreferencesKeys.USERNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferenceManager(private val context: Context) {

    /* Create a Preferences DataStore */

    private val preferenceDataStore = context.createDataStore(name = PREFERENCE_NAME)

    /*Store user data*/

    suspend fun storeUserData(userName: String, age: Int, location: String, status: Boolean) {
        preferenceDataStore.edit { profile ->
            profile[USERNAME] = userName
            profile[AGE] = age
            profile[LOCATION] = location
            profile[IS_ACCOUNT_ACTIVE] = status
        }
    }

    /*Create An User flow */

    val userNameFlow: Flow<String> = preferenceDataStore.data.map {
        it[USERNAME] ?: ""
    }.catch { exception -> handleException(exception) }

    /*Create An AGE flow */

    val userAgeFlow: Flow<Int> = preferenceDataStore.data.map {
        it[AGE] ?: 0
    }.catch { exception -> handleException(exception) }

    /*Create user Location  flow */

    val userLocationFlow: Flow<String> = preferenceDataStore.data.map {
        it[LOCATION] ?: ""
    }.catch { exception -> handleException(exception) }

    /*Create user Account Status  flow */

    val userAccountStatusFlow: Flow<Boolean> = preferenceDataStore.data.map {
        it[IS_ACCOUNT_ACTIVE] ?: false
    }.catch { exception -> handleException(exception) }


    private fun handleException(exception: Throwable) {
        if (exception is IOException) {
            exception.printStackTrace()
            //Handle Exception
        } else {
            throw exception
        }
    }

}