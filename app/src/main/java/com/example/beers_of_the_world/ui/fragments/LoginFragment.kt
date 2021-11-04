
package com.example.beers_of_the_world.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.beers_of_the_world.R
import com.example.beers_of_the_world.databinding.FragmentLoginBinding
import com.example.beers_of_the_world.databinding.FragmentMainBinding
import com.example.beers_of_the_world.ui.activities.MainActivity
import com.example.beers_of_the_world.viewModel.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*subscribeUI()*/

        //buildListeners()


    }

   /* private fun buildListeners() {
        buttonPassListener()
    }

    private fun buttonPassListener() {
        passToSecondAct()
    }

    private fun passToSecondAct() {
        binding.buttonpass.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }*/

    /*private fun bLoginListener() {
        binding.buttonpass.setOnClickListener {
            //TODO GET PREFIX
            getToken()
        }
    }*/

    /*private fun getToken() {
        ViewModel.getToken()
    }*/
}