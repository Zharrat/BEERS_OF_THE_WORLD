/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.model

import com.google.gson.annotations.SerializedName

//https://medium.com/droid-latam/sealed-classes-en-kotlin-por-qu%C3%A9-y-para-qu%C3%A9-67bb9e1f2e5e
//https://www.adictosaltrabajo.com/2019/06/27/clases-selladas-y-enumerados-en-kotlin/
sealed class CallValuationSurveyQualityRequest(@SerializedName("review") val quality: Int) {
    object Dislike : CallValuationSurveyQualityRequest(0)
    object Like : CallValuationSurveyQualityRequest(1)
}