package com.blackangeldragon.carritosv2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blackangeldragon.carritosv2.databinding.ActivityMainBinding

class configCarrera : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button
    }
}