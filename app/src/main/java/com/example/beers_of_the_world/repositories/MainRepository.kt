

package com.example.beers_of_the_world.repositories

import com.example.beers_of_the_world.data.network.Api

class MainRepository
    (private val api: Api) : BaseRepository() {
    suspend fun getBeer() = safeApiCall { api.getBeer() }
}