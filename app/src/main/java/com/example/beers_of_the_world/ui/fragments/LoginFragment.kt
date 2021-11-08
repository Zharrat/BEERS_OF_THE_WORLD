package com.example.beers_of_the_world.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.beers_of_the_world.database.RegisterDatabase
import com.example.beers_of_the_world.database.RegisterEntity
import com.example.beers_of_the_world.database.RegisterRepository
import com.example.beers_of_the_world.databinding.FragmentLoginBinding
import com.example.beers_of_the_world.repositories.UserPreferences
import com.example.beers_of_the_world.ui.activities.MainActivity
import com.example.beers_of_the_world.viewModel.LoginViewModel
import com.example.beers_of_the_world.viewModel.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel

    lateinit var userPreferences : UserPreferences
    var name=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflates the layout for this fragment
        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)


        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                //displayUsersList()
                loginViewModel.doneNavigatingRegiter()
            }
        })

        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "PLEASE FILL ALL FIELDS", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(
                    requireContext(),
                    "USER DOESNT EXIST, PLEASE REGISTER BEFORE LOGIN",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "PLEASE CHECK YOUR PASSWORD", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        //We observe before pass to the second activity.
        loginViewModel.navigatetoUserDetails.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                //It it changes to true, we go to the second activity
                loggedOK()
                loginViewModel.doneNavigatingUserDetails()
            }
        })

        buildListeners()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*subscribeUI()*/
    }


    //We create the listeners.
    private fun buildListeners() {
        onFragmentBackPressed(closeSession = true)
        //buttonPassToRegister()
        buttonPassToLogin()
        buttonPassWithoutLogin()
        binding.ibregister.setOnClickListener {
            manageFragmentsDirections()
        }
    }

 /*   private fun observeData() {

        //Updates name
        userPreferences.userNameFlow.asLiveData().observe(viewLifecycleOwner, {
            name = it
            binding.userNameTextField.text = name
        })
    }*/


    private fun saveData() {

        //Gets the user input and saves it
           var name = binding.userNameTextField.text.toString()

            //Stores the name.
            GlobalScope.launch {
                userPreferences.storeUser(name)
            }



    }

    private fun manageFragmentsDirections() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun buttonPassToLogin() {
        binding.submitButton.setOnClickListener() {
            //Toast.makeText(getActivity(), "LAUNCH LOGIN FRAGMENT", Toast.LENGTH_LONG).show();
            //loginViewModel.loginButton()
            sendNameAndPassword()
            //We compare the data of the user.


        }
    }

    private fun buttonPassWithoutLogin() {
        binding.btenter.setOnClickListener() {
            //Toast.makeText(getActivity(), "LAUNCH LOGIN ACTIVITY", Toast.LENGTH_LONG).show();

            //Starts the second activity.
            startActivity(Intent(requireContext(), MainActivity::class.java))
            //finish the current activity.
            appFinish()

        }

    }

    fun sendNameAndPassword() {
        var username = binding.userNameTextField.text.toString()
        var password = binding.passwordTextField.text.toString()

        loginViewModel.loginButton(username, password)

    }

    private fun loggedOK() {

        //We get the username of the user.
        var userName = binding.userNameTextField.text.toString()
        saveData()
        startActivity(Intent(requireContext(), MainActivity::class.java))

        //finish the current activity.
        appFinish()
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






