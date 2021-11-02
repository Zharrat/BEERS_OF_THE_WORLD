package com.example.beers_of_the_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beers_of_the_world.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}