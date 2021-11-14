

package com.example.beers_of_the_world.ui.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.beers_of_the_world.R
import com.example.beers_of_the_world.R.drawable.dialog_bg
import com.example.beers_of_the_world.databinding.FragmentDetailBinding
import com.example.beers_of_the_world.databinding.FragmentDialogBinding
import com.example.beers_of_the_world.databinding.FragmentMainBinding
import com.example.beers_of_the_world.viewModel.DialogViewModel
import com.example.beers_of_the_world.viewModel.MainViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DialogFragment() : androidx.fragment.app.DialogFragment() {

    private var param1: String? = null
    private var param2: String? = null

    /*private lateinit var clickListener: onClickListener*/
    private lateinit var binding: FragmentDialogBinding

    //To can access to the methods of the class
    private lateinit var dialogViewModel: DialogViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*clickListener = context as ClickListener*/

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {

            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        /*return binding.root*/
        // Inflate the layout for this fragment

        Log.d("DialogFragment", "$param1::$param2")
        //val view = inflater.inflate(R.layout.fragment_dialog, container, false)
        binding.titleText.text= param1
        binding.subtitleText.text=param2
        /*param1.also { view.findViewById<TextView>(R.id.titleText).text = it }
        param2.also { view.findViewById<TextView>(R.id.subtitleText).text = it }*/
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val alertDialog = alertDialogBuilder.create()

        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();




        /*binding.btAccept.setOnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Are you sure, Yow wanted to make decision")
            alertDialogBuilder.setPositiveButton(
                "Yes"
            ) { dialog, which ->
                clickListener.onDoneClicked(param1!!, param2!!)
                this@DialogFragment.dismiss()
                dialog?.dismiss()
            }
            alertDialogBuilder.setNegativeButton(
                "No"
            ) { dialog, which ->
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }

        }*/

        return binding.root
        //return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogViewModel = ViewModelProvider(requireActivity()).get(DialogViewModel::class.java)
        setupClickListeners()



    }

    //To can access from all classes
    companion object {
        const val TAG = "DialogFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DialogFragment.
         */
        // TODO: Rename and change types and number of parameters

        //To put the strings in the new alert dialog.
        @JvmStatic
        fun newDialog(param1: String, param2: String) =
            DialogFragment().apply {
                arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setupClickListeners(){



        binding.btAccept.setOnClickListener {
            activity?.finish()
           /* dialogViewModel.sendResponse(true)
            dismiss()*/

        }

        binding.btCancel.setOnClickListener {
            /*this@DialogFragment.dismiss()
            dialog?.dismiss()*/
            dialogViewModel.sendResponse(false)
            dismiss()

        }
    }


    private fun setTextInView() {

        binding.titleText.text= param1
        binding.subtitleText.text= param2
    }


    interface onButtonClickListener {
        fun onButtonClick(param1: String, param2: String)
    }


}