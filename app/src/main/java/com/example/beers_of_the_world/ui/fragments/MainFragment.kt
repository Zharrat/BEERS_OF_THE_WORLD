/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beers_of_the_world.BeerResponse
import com.example.beers_of_the_world.viewModel.MainViewModel
import com.example.beers_of_the_world.databinding.FragmentMainBinding
import com.example.beers_of_the_world.ui.adapter.BeerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainFragment : Fragment() ,BeerAdapter.OnItemClickListener{

    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController
    private lateinit var beerAdapter: BeerAdapter



    //To can access to the methods of the class
    private val MainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //We show the list of beers in the first fragment.
        /*MainViewModel.getListOfBeers()*/
        MainViewModel.getBeers()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflates the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //We subscribe to the data changes.

        subscribeUI()
        buildListeners()

    }

    /*   private fun initRecycler(abeers: List<BeerResponse>) {
           buildAdapter(abeers)
           *//*binding.rvBeers.layoutManager = LinearLayoutManager(binding.rvBeers.context)

        val adapter= BeerAdapter(abeers)
        binding.rvBeers.adapter=adapter*//*

    }*/

    private fun initRecycler(abeers: List<BeerResponse>) {
        buildAdapter(abeers)


    }

    private fun buildAdapter(abeers: List<BeerResponse>) {
        beerAdapter = BeerAdapter(this,abeers)

        binding.rvBeers.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = beerAdapter
        }
    }

    override fun onItemClick(position: Int) {
        //Toast.makeText(getActivity(), "Ha pulsado $position", Toast.LENGTH_LONG).show();
        MainViewModel.getBeerByPos(position)

    }




    //Listeners
    private fun buildListeners() {

        onFragmentBackPressed(closeSession = true)
    }

    //We subscribe to the data changes.
    private fun subscribeUI() {
        //observeItems()
        observeRVBeer()
        observeSelectedBeer()
    }

    private fun getABeer(position: Int) {
        MainViewModel.getBeerByPos(position)
    }

    //Receive an object and launch the second fragment.
    fun manageFragmentsDirections(beer: BeerResponse) {

        val action: NavDirections = MainFragmentDirections.actionGlobalDetailFragment(beer)
        findNavController().navigate(action)

    }


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


    //Observe the changes.
   /* private fun observeItems() {
        MainViewModel.beerSelected.observe(viewLifecycleOwner, Observer {
            it?.let {
                manageFragmentsDirections(it)
            }
        })
    }*/

    //Function to display the array of names in the listview.
    /*   private fun displayNames(aNames: List<String>) {
           listAdapter =
               activity?.let {
                   ArrayAdapter<String>(it, R.layout.simple_list_item_1, aNames)
               }

       }*/

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
                            question()
                        } else {
                            findNavController().popBackStack()
                        }
                    }
                }
            })
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
    }

}









