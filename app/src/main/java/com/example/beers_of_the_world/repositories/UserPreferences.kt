

package com.example.beers_of_the_world.repositories

import androidx.datastore.preferences.preferencesKey
import com.example.beers_of_the_world.ui.fragments.RegisterFragment
import kotlinx.coroutines.flow.Flow


class UserPreferences(context: RegisterFragment) {

    //Create the dataStore
    private val dataStore = context.createDataStore(name = "user_prefs")

    //Create some keys
    companion object {
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
    }

    //Store user data
    suspend fun storeUser(name: String) {
        dataStore.edit {
            it[USER_NAME_KEY] = name
        }
    }

    //Create a name flow
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

}