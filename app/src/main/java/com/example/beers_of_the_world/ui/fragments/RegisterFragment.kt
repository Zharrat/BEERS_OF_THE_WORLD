package com.example.beers_of_the_world.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.beers_of_the_world.database.RegisterDatabase
import com.example.beers_of_the_world.database.RegisterEntity
import com.example.beers_of_the_world.database.RegisterRepository
import com.example.beers_of_the_world.databinding.FragmentRegisterBinding
import com.example.beers_of_the_world.repositories.UserPreferences
import com.example.beers_of_the_world.viewModel.LoginViewModel
import com.example.beers_of_the_world.viewModel.RegisterViewModel
import com.example.beers_of_the_world.viewModel.RegisterViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var registerViewModel: RegisterViewModel

    lateinit var userPreferences: UserPreferences
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get reference to our userManager class
        userPreferences = UserPreferences(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)


        registerViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i("MYTAG", "insidi observe")
                //displayUsersList()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG", it.toString() + "000000000000000000000000")
        })


        registerViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.donetoast()
            }
        })

        registerViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.donetoastUserName()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        /*subscribeUI()*/
        buildListeners()

    }

    fun sendUser() {

        var firstName = binding.firstNameTextField.text.toString()
        var lastName = binding.secondNameTextField.text.toString()
        var userName = binding.userNameTextField.text.toString()
        var password = binding.passwordTextField.text.toString()


        val registerEntity = RegisterEntity(0, firstName, lastName, userName, password)
        //Toast.makeText(getActivity(), "" + firstName, Toast.LENGTH_LONG).show();
        registerViewModel.sumbitButton(registerEntity)

    }

    private fun buildListeners() {
        onFragmentBackPressed(closeSession = true)
        binding.submitButton.setOnClickListener {
            //sendUser()
            //comeBackFirstFragment()
            registerQuestion()
            //comeBackFirstFragment()
        }
    }

    private fun observeData() {

        //Updates name
        userPreferences.userNameFlow.asLiveData().observe(viewLifecycleOwner, {

            name = it
            binding.tvusername.text= it.toString()

        })
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
                sendUser()
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
            .setMessage("NOT REGISTERED.¿COME BACK TO LOGIN?")
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