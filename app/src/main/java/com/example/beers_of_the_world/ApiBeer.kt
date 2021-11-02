package com.example.beers_of_the_world

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//Es un modelo de datos.


@Parcelize
data class ApiBeer(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?
): Parcelable


