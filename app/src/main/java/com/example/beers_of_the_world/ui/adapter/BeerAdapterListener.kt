

package com.example.beers_of_the_world.ui.adapter

import com.example.beers_of_the_world.BeerResponse

interface BeerAdapterListener {
    fun onBeerClick(beerResponse: BeerResponse)
}