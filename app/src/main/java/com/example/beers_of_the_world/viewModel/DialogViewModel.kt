/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {
    val response = MutableLiveData<Boolean>()

    fun sendResponse(res: Boolean) {
        response.value= res

    }

}