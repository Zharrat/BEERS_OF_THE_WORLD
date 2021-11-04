/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beers_of_the_world.APIService
import com.example.beers_of_the_world.BeerResponse
import com.example.beers_of_the_world.Event
import com.example.beers_of_the_world.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*class MainViewModel (private val repository: MainRepository)*/
class MainViewModel : ViewModel() {


    private val _mainResponse = MutableLiveData<Event<Resource<List<BeerResponse>>>>()
    val mainResponse: LiveData<Event<Resource<List<BeerResponse>>>> get() = _mainResponse

    private val _beerSelected: MutableLiveData<BeerResponse> = MutableLiveData()
    val beerSelected: LiveData<BeerResponse> get() = _beerSelected

    private val _beersList: MutableLiveData<List<BeerResponse>> = MutableLiveData()
    val beersList: LiveData<List<BeerResponse>> get() = _beersList

    private val _beersNamesList: MutableLiveData<List<String>> = MutableLiveData()
    val beersNamesList: LiveData<List<String>> get() = _beersNamesList

    fun setBeer(beers: List<BeerResponse>) {
        _beersList.postValue(beers)
    }

    fun setBeerNames(namesList: List<String>) {
        _beersNamesList.postValue(namesList)
    }

    fun setBeers(beers: List<BeerResponse>) {
        _beersList.postValue(beers)
    }

    public fun getListOfBeers() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(APIService::class.java).getBeer("").execute()
            val beert = response.body()
            beert?.let {
                initAdapter(it)
                //var beeres = beers[id]
            }

        }
    }

    public fun getBeers() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(APIService::class.java).getBeer("").execute()
            val beert = response.body()
            beert?.let {
                initAdapter1(it)
                //var beeres = beers[id]
            }

        }
    }



    //Function to return only a beer. The beer is downloaded from the API.
    public fun getBeerByPos(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(APIService::class.java).getBeer("").execute()
            val beert = response.body()
            beert?.let {
                initAdapter2(id, it)

            }

        }
    }



    private fun initAdapter1(beers: List<BeerResponse>) {
        /*var beer = beers[id]*/
        setBeers(beers)
    }

    private fun initAdapter2(id: Int, beers: List<BeerResponse>) {
        var beer = beers[id]
        setBeerSelected(beer)
    }

    private fun setBeerSelected(beer: BeerResponse) {
        _beerSelected.postValue(beer)
    }


    private fun initAdapter(beers: List<BeerResponse>) {
        var aNames = beers.map { it.name }
        setBeerNames(aNames as List<String>)

    }

    //Coroutine to get the beers in the background.
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2/beers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}