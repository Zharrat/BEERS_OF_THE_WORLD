
package com.example.beers_of_the_world.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.beers_of_the_world.R
import com.example.beers_of_the_world.databinding.FragmentLoginBinding
import com.example.beers_of_the_world.databinding.FragmentMainBinding
import com.example.beers_of_the_world.ui.activities.MainActivity
import com.example.beers_of_the_world.viewModel.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflates the layout for this fragment
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*subscribeUI()*/

        buildListeners()


    }

    private fun buildListeners() {
        onFragmentBackPressed(closeSession = true)
        buttonPassToRegister()
        buttonPassToLogin()
        buttonPassWithoutLogin()
    }

    private fun buttonPassToRegister() {
        binding.ivregister.setOnClickListener() {
            Toast.makeText(getActivity(), "LAUNCH REGISTER FRAGMENT", Toast.LENGTH_LONG).show();
            //We create an object of an user.
        }
    }

    private fun buttonPassToLogin() {
        binding.iblogin.setOnClickListener() {
            Toast.makeText(getActivity(), "LAUNCH LOGIN FRAGMENT", Toast.LENGTH_LONG).show();


        }
    }

    private fun buttonPassWithoutLogin() {
        binding.btenter.setOnClickListener() {
            //Toast.makeText(getActivity(), "LAUNCH LOGIN ACTIVITY", Toast.LENGTH_LONG).show();
            startActivity(Intent(requireContext(), MainActivity::class.java))
            appFinish()

        }

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
            "¿EXIT WITHOUT REGISTERING?",
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
            .setMessage("YOU ARE NOT REGISTERED, ¿EXIT?")
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






