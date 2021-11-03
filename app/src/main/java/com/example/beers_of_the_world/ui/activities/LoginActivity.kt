

package com.example.beers_of_the_world.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beers_of_the_world.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}