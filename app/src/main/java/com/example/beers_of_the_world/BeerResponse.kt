

package com.example.beers_of_the_world

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerResponse (
    @SerializedName("id")
    val id:Int?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("image_url")
    val image_url: String?,
    @SerializedName("description")
    val description: String?
):Parcelable



