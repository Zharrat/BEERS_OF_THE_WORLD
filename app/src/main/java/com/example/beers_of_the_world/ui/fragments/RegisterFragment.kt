

package com.example.beers_of_the_world.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.beers_of_the_world.databinding.FragmentRegisterBinding
import com.example.beers_of_the_world.viewModel.LoginViewModel
import com.example.beers_of_the_world.viewModel.MainViewModel
import com.example.beers_of_the_world.viewModel.RegisterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
        binding.btregister.setOnClickListener {
            registerQuestion()
            //comeBackFirstFragment()
        }
    }

    private fun registerQuestion() {
        okQuestion(
            "REGISTER",
            "NEW USER",
            {

            })
    }



    fun okQuestion(
        title: String,
        message: String,
        yesFunc: () -> Unit,
        noFunc: (() -> Unit)? = null,
        yesMessage: String? = "YES",
        noMessage: String? = "NO"
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("WELCOME")
            .setMessage("¿DO YOU WANT TO BE REGISTERED?")
            .setPositiveButton("YES") { dialog, _ ->
                yesFunc()
                dialog.dismiss()
                //appFinish()
                comeBackFirstFragment()
            }
            .setNegativeButton("NO") { dialog, _ ->
                noFunc?.let { noFunc() }
                dialog.dismiss()
            }
            .show()
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
    private fun question() {
        finalQuestion(
            "EXIT",
            "¿COME BACK?",
            {
                //appFinish()
            })
    }

    private fun comeBackFirstFragment() {
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
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
            .setMessage("¿COME BACK TO LOGIN?")
            .setPositiveButton("YES") { dialog, _ ->
                yesFunc()
                dialog.dismiss()
                //appFinish()
                comeBackFirstFragment()
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

    companion object {

    }
}