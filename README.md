
# Q What is Datastore ?
* Jetpack DataStore is a data storage solution.
*	It allows you to store key-value pairs like “SharedPrefrences” of typed object with protocal buffers.
*	DataStore uses Kotlin, Coroutines and Flow to store data asynchronously with consistency and transaction support .
*	it’s the new data storage solution which is the replacement of SharedPrefrences .

# Q why DataStore ?
* Build with Kotlin, Coroutines and Flow.
* SharedPreference has some drawbacks like it provided synchronous APIs -but it’s not MAIN-thread-safe! whereas DataStore is safe to use in UI thread because it uses Dispatchers.IO under the hood
* It’s safe from runtime exceptions!
* It also provides a way to migrate from SharedPreferences
* It provides Type safety! (Using Protocol buffers).

# DataStore provides two different types of implementations to store data.
* Preference DataStore — This uses key-value pairs to store data. But it doesn’t provide type-safety :
* Proto DataStore — It stores data as a custom type with specified schema using Protocol Buffers

# Room vs DataStore
* If you have a need for partial updates, referential integrity, or large/complex datasets, you should consider using Room instead of DataStore. 
* DataStore is ideal for small or simple datasets and does not support partial updates or referential integrity.

## Preferences DataStore API is similar to SharedPreferences with several notable differences:
*	Handles data updates transactionally
*	Exposes a Flow representing the current state of data
*	Does not have data persistent methods (apply(), commit())
*	Does not return mutable references to its internal state
*	Exposes an API similar to Map and MutableMap with typed keys

# Preferences DataStore Setup 

## Adding dependencies
* Update the build.gradle file to add the following the Preference DataStore dependency:

```groovy
dependencies {
    // Preferences DataStore
    implementation "androidx.datastore:datastore-preferences:1.0.0-alpha01"
}
```

## Create a Preferences DataStore
* Use the Context.createDataStore() extension function to create an instance of DataStore<Preferences>. The mandatory name parameter is the name of the Preferences DataStore.

```kotlin
val dataStore: DataStore<Preferences> = context.createDataStore(
  name = "settings"
)
```

## Read from a Preferences DataStore
 ** Because Preferences DataStore does not use a predefined schema, you must use Preferences.preferencesKey() to define a key for each value that you need to store in the DataStore<Preferences> instance. Then, use the DataStore.data property to expose the appropriate stored value using a Flow

```kotlin
object PreferencesKeys {
    const val PREFERENCE_NAME = "user_profile"
    val USERNAME = preferencesKey<String>("username")
    val AGE = preferencesKey<Int>("age")
    val LOCATION = preferencesKey<String>("location")
    val IS_ACCOUNT_ACTIVE = preferencesKey<Boolean>("status")
}
```

```kotlin
val userNameFlow: Flow<String> = preferenceDataStore.data.map {
    it[USERNAME] ?: ""
}.catch { exception -> handleException(exception) }
```

