/*
 * Copyright (c) FS
 */

package com.example.beers_of_the_world.ui.fragments

import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.beers_of_the_world.viewModel.BaseViewModel

abstract class BaseFragment <VM : BaseViewModel,B : ViewBinding> : Fragment() {


    //protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: B
    //protected lateinit var remoteDataSource: RemoteDataSource
    protected lateinit var viewModel: VM
    private val TAG = BaseFragment::class.java.name
    private lateinit var connectivityManager: ConnectivityManager
    private var authToken: String? = null
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

}