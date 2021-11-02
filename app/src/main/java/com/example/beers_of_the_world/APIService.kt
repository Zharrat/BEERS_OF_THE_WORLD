package com.example.beers_of_the_world

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getBeer(@Url url:String): Call<List<BeerResponse>>

    fun getABeer(@Url url:String): Call<BeerResponse>
}