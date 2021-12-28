package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.adapters.CardMemeAdapter
import com.example.myapplication.data.responses.MemeItemResponse
import com.example.myapplication.data.service.ApiService
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.ViewModelFactory
import com.example.myapplication.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.rvMemeList.layoutManager = GridLayoutManager(this, 3)
        val adapter = CardMemeAdapter(this)
        binding.rvMemeList.adapter = adapter
        viewModel.getMemeList().observe(this) { listMeme ->
            adapter.setData(listMeme)
        }
    }
}