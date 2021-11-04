/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beers_of_the_world.BeerResponse
import com.example.beers_of_the_world.User

class RegisterViewModel : ViewModel() {

    private val _usersList: MutableLiveData<List<User>> = MutableLiveData()
    val usersList: MutableLiveData<List<User>> get() = _usersList

    private val _registeredUser: MutableLiveData<User> = MutableLiveData()
    val registeredUser : LiveData<User> get() = _registeredUser

    private val _usersNames: MutableLiveData<List<String>> = MutableLiveData()
    val usersName : MutableLiveData<List<String>> get() = _usersNames


    public fun registerUser() {

    }

    private fun getNames(beers: List<BeerResponse>) {
        var aNames = beers.map { it.name }
        setUserNames(aNames as List<String>)

    }
    fun setUserNames(aNames: List<String>) {
        _usersNames.postValue(aNames)
    }
}