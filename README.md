
Preferences DataStore overview

Preferences DataStore API is similar to SharedPreferences with several notable differences:
•	Handles data updates transactionally
•	Exposes a Flow representing the current state of data
•	Does not have data persistent methods (apply(), commit())
•	Does not return mutable references to its internal state
•	Exposes an API similar to Map and MutableMap with typed keys
Let's see how to add it to the project and migrate SharedPreferences to DataStore.

Adding dependencies
Update the build.gradle file to add the following the Preference DataStore dependency:

dependencies {
    // Preferences DataStore
    implementation "androidx.datastore:datastore-preferences:1.0.0-alpha01"

    // Proto DataStore
    implementation  "androidx.datastore:datastore-core:1.0.0-alpha01"
}

Create a Preferences DataStore
Use the Context.createDataStore() extension function to create an instance of DataStore<Preferences>. The mandatory name parameter is the name of the Preferences DataStore.

val dataStore: DataStore<Preferences> = context.createDataStore(
  name = "settings"
)



Read from a Preferences DataStore
Because Preferences DataStore does not use a predefined schema, you must use Preferences.preferencesKey() to define a key for each value that you need to store in the DataStore<Preferences> instance. Then, use the DataStore.data property to expose the appropriate stored value using a Flow


object PreferencesKeys {
    const val PREFERENCE_NAME = "user_profile"
    val USERNAME = preferencesKey<String>("username")
    val AGE = preferencesKey<Int>("age")
    val LOCATION = preferencesKey<String>("location")
    val IS_ACCOUNT_ACTIVE = preferencesKey<Boolean>("status")
}


val userNameFlow: Flow<String> = preferenceDataStore.data.map {
    it[USERNAME] ?: ""
}.catch { exception -> handleException(exception) }


