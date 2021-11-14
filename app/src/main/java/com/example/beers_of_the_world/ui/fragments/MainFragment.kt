/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.ui.fragments

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beers_of_the_world.BeerResponse
import com.example.beers_of_the_world.viewModel.MainViewModel
import com.example.beers_of_the_world.databinding.FragmentMainBinding
import com.example.beers_of_the_world.repositories.UserPreferences
import com.example.beers_of_the_world.ui.adapter.BeerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.content.ContextWrapper

import android.app.Activity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.beers_of_the_world.viewModel.DialogViewModel
import com.example.beers_of_the_world.viewModel.SharedViewModel


class MainFragment : Fragment(), BeerAdapter.OnItemClickListener,DialogFragment.onButtonClickListener  {

    //To call to elemets from the layout
    private lateinit var binding: FragmentMainBinding


    private lateinit var navController: NavController
    private lateinit var beerAdapter: BeerAdapter

    lateinit var userPreferences: UserPreferences
    var name = ""

    //To can access to the methods of the class
    private val MainViewModel: MainViewModel by viewModels()

    private val DialogViewModel: DialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get reference to our userPreferences class
        userPreferences = activity?.let { UserPreferences(it) }!!

        //We show the list of beers in the first fragment.
        //We obtain the beers from the api.
        MainViewModel.getBeers()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //We subscribe to the data changes.
        subscribeUI()
        observeData()
        //We create the listeners.
        buildListeners()

    }

    private fun initRecycler(abeers: List<BeerResponse>) {
        buildAdapter(abeers)

    }

    private fun buildAdapter(abeers: List<BeerResponse>) {
        beerAdapter = BeerAdapter(this, abeers)

        binding.rvBeers.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = beerAdapter
        }
    }

    //The action when the user clicks in the one item.
    override fun onItemClick(position: Int) {
        //Toast.makeText(getActivity(), "Ha pulsado $position", Toast.LENGTH_LONG).show();
        MainViewModel.getBeerByPos(position)

    }

    override fun onButtonClick(param1: String, param2: String) {

    }


    //The listeners
    private fun buildListeners() { //We touch the back button.
        onFragmentBackPressed(closeSession = true)




    }

    //We subscribe to the data changes.
    private fun subscribeUI() {

        //We observe the changes in the recycler view of the beers.
        observeRVBeer()
        //We observe the changes in the selected beer.
        observeSelectedBeer()
        observeResponse()

    }

    private fun observeData() {

        //Updates name
        userPreferences.userNameFlow.asLiveData().observe(viewLifecycleOwner, {

            //We write the userName in the fragment
            name = it
            binding.tvUserName.text=it

        })
    }


    private fun getABeer(position: Int) {
        MainViewModel.getBeerByPos(position)
    }

    //Receive an object and launch the detail fragment.
    fun manageFragmentsDirections(beer: BeerResponse) {

        val action: NavDirections = MainFragmentDirections.actionGlobalDetailFragment(beer)
        findNavController().navigate(action)

    }

    //Observers
    private fun observeRVBeer() {
        MainViewModel.beersList.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(getActivity(), "UPDATED DATA", Toast.LENGTH_LONG).show();
                initRecycler(it)
            }
        })
    }

    private fun observeSelectedBeer() {
        MainViewModel.beerSelected.observe(viewLifecycleOwner, Observer {
            it?.let {
                manageFragmentsDirections(it)
            }
        })
    }

    private fun observeResponse() {
        DialogViewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let {
                //Toast.makeText(getActivity(), "CLOSED", Toast.LENGTH_LONG).show();
                getResponse(it)
            }
        })
    }

    //Function when we press the back button.
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
                            onDialogDisplay("BEERS OF THE WORLD","EXIT¿ARE YOU SURE?")
                            //question()
                        } else {
                            //onDialogDisplay("Estas seguro","seguriiiisimo")
                            findNavController().popBackStack()
                        }
                    }
                }
            })
    }

    //We launch the question.
    /*fun question() {
        val newFragment = DialogFragment.newDialog("Fernando", "Es el mejor")
        *//*fragmentManager?.let { it1 -> newFragment.show(it1, DialogFragment.TAG) }*//*
        newFragment.show(getSupportFragmentManager(), "custom")

    }*/

    //Function to create the alert dialog
    fun onDialogDisplay(param1: String, param2: String) {
        val newFragment = DialogFragment.newDialog("$param1","$param2")
        fragmentManager?.let { it1 -> newFragment.show(it1, DialogFragment.TAG) }
    }

    fun getResponse(response : Boolean) {

        appFinish()


    }





    //We launch the question.
    fun question() {
        finalQuestion(
            "EXIT",
            "¿ARE YOU SURE?",
            {
                appFinish()
            })
    }

    //We ask about going out from the app.
    fun finalQuestion(
        title: String,
        message: String,
        yesFunc: () -> Unit,
        noFunc: (() -> Unit)? = null,
        yesMessage: String? = "YES",
        noMessage: String? = "NO"
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("BEERS OF THE WORLD")
            .setMessage("¿EXIT?")
            .setPositiveButton("YES") { dialog, _ ->
                yesFunc()
                dialog.dismiss()
                appFinish()
            }
            .setNegativeButton("NO") { dialog, _ ->
                noFunc?.let { noFunc() }
                dialog.dismiss()
            }
            .show()
    }

    //Exit. App finishes.
    fun appFinish() {
        activity?.finish()
        activity?.finish()
    }

    /*override fun onDoneClicked(param1: String, param2: String) {
        TODO("Not yet implemented")
    }*/


}









