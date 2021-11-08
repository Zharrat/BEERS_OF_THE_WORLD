

package com.example.beers_of_the_world.repositories

import com.example.beers_of_the_world.database.network.Api

class MainRepository
    (private val api: Api) : BaseRepository() {
    suspend fun getBeer() = safeApiCall { api.getBeer() }
}