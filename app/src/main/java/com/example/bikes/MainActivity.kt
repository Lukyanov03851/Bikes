package com.example.bikes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bikes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mBinding = binding
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}