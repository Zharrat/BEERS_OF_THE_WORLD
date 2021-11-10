package com.example.beers_of_the_world.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "Utils"
const val JPG_EXTENSION = ".jpg"
const val MP4_EXTENSION = ".mp4"

//Corroutine
fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.punkapi.com/v2/beers/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}





