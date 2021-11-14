/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.beers_of_the_world.BeerResponse
import com.example.beers_of_the_world.viewModel.DetailViewModel
import com.example.beers_of_the_world.R
import com.example.beers_of_the_world.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso


class DetailFragment : Fragment() {
    private lateinit var name: String
    private lateinit var beerr: BeerResponse
    private lateinit var binding: FragmentDetailBinding
    private lateinit var navController: NavController


    //To can access to the methods of the class
    private val DetailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
        //binding.tv.text=tagline
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beerr = arguments?.let { DetailFragmentArgs.fromBundle(it).beer }!!
        binding.tv.text = beerr.description.toString()

        receiveObject()

        subscribeUI()

        buildListeners()
    }

    fun receiveObject(){
        beerr = arguments?.let { DetailFragmentArgs.fromBundle(it).beer }!!
        binding.tv.text = beerr.description.toString()
        Picasso.get().load(beerr.image_url).into(binding.ivBeerDetail)
    }

    fun subscribeUI() {

    }

    fun buildListeners() {
        //If we are in the second fragment and press the back button.CloseSession = false
        onFragmentBackPressed(closeSession = false)


    }

    fun onDialogDisplay(param1: String, param2: String) {
        val newFragment = DialogFragment.newDialog("$param1","$param2")
        fragmentManager?.let { it1 -> newFragment.show(it1, DialogFragment.TAG) }
    }

    //If we press the back button ,to go back in the app.
    fun onFragmentBackPressed(
        closeSession: Boolean,
        func: (() -> Unit)? = null
    ) {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (func != null) {
                        func()
                    } else {
                        if (closeSession) {
                            //Closes the activity
                            activity!!.finish()
                        } else {
                                //onDialogDisplay("Estas seguro","seguriiiisimo")
                            //Turns to the previous fragment
                            findNavController().navigate(R.id.mainFragment)

                        }
                    }
                }
            })
    }
}