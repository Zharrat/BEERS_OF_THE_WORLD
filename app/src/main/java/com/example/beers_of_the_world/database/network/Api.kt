

package com.example.beers_of_the_world.database.network

import com.example.beers_of_the_world.BeerResponse
import retrofit2.http.*


interface Api {

    @GET("cervezas")
    suspend fun getBeer(
    ): List<BeerResponse>



}