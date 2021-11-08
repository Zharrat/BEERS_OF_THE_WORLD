/*
 * Copyright (c) FS
 */



package com.example.beers_of_the_world.database.objects

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    @SerializedName("firstname")
    val id:String?,
    @SerializedName("lastname")
    val name:String?,
    @SerializedName("username")
    val tagline: String?,
    @SerializedName("password")
    val image_url: String?
): Parcelable