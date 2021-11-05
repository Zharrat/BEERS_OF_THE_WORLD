package com.example.beers_of_the_world.viewModel

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.beers_of_the_world.BeerResponse
import com.example.beers_of_the_world.database.RegisterEntity
import com.example.beers_of_the_world.database.RegisterRepository
import com.example.beers_of_the_world.database.objects.User
import com.example.beers_of_the_world.ui.fragments.DetailFragmentArgs
import kotlinx.coroutines.*


class RegisterViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    private val _usersList: MutableLiveData<List<User>> = MutableLiveData()
    val usersList: MutableLiveData<List<User>> get() = _usersList

    private val _registeredUser: MutableLiveData<User> = MutableLiveData()
    val registeredUser: LiveData<User> get() = _registeredUser

    private val _usersNames: MutableLiveData<List<String>> = MutableLiveData()
    val usersName: MutableLiveData<List<String>> get() = _usersNames

    init {
        Log.i("MYTAG", "init")
    }


    private var userdata: String? = null

    var userDetailsLiveData = MutableLiveData<Array<RegisterEntity>>()

    @Bindable
    var inputFirstName = MutableLiveData<String>()

    @Bindable
    val inputLastName = MutableLiveData<String>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    private fun getNames(beers: List<BeerResponse>) {
        var aNames = beers.map { it.name }
        setUserNames(aNames as List<String>)

    }

    fun setUserNames(aNames: List<String>) {
        _usersNames.postValue(aNames)
    }

    public fun sumbitButton(registerEntity: RegisterEntity) {


        inputFirstName.value= registerEntity.firstName
        inputLastName.value= registerEntity.lastName
        inputUsername.value= registerEntity.userName
        inputPassword.value= registerEntity.passwrd


        Log.i("MYTAG", "Inside SUBMIT BUTTON")
        if (inputFirstName.value == null || inputLastName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
            //withContext(Dispatchers.IO) {
                val usersNames = repository.getUserName(inputUsername.value!!)
                Log.i("MYTAG", usersNames.toString() + "------------------")
                if (usersNames != null) {
                    _errorToastUsername.value = true
                    Log.i("MYTAG", "Inside if Not null")
                } else {
                    Log.i("MYTAG", userDetailsLiveData.value.toString() + "ASDFASDFASDFASDF")
                    Log.i("MYTAG", "OK im in")
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val email = inputUsername.value!!
                    val password = inputPassword.value!!
                    Log.i("MYTAG", "insidi Sumbit")
                    insert(RegisterEntity(0, firstName, lastName, email, password))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    _navigateto.value = true
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
        Log.i("MYTAG", "Done navigating ")
    }

    fun donetoast() {
        _errorToast.value = false
        Log.i("MYTAG", "Done taoasting ")
    }

    fun donetoastUserName() {
        _errorToast.value = false
        Log.i("MYTAG", "Done taoasting  username")
    }

    private fun insert(user: RegisterEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

//    fun clearALl():Job = viewModelScope.launch {
//        repository.deleteAll()
//    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}