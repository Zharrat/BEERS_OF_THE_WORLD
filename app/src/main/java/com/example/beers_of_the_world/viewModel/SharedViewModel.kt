/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val name = MutableLiveData<String>()

    fun sendName(text: String) {
        name.value = text
    }

}