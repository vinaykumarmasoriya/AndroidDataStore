package com.android.datastore.preference

import androidx.datastore.preferences.preferencesKey

object PreferencesKeys {
    const val PREFERENCE_NAME = "user_profile"
    val USERNAME = preferencesKey<String>("username")
    val AGE = preferencesKey<Int>("age")
    val LOCATION = preferencesKey<String>("location")
    val IS_ACCOUNT_ACTIVE = preferencesKey<Boolean>("status")
}