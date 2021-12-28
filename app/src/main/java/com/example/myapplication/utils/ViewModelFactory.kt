package com.example.myapplication.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.DetailViewModel
import com.example.myapplication.viewmodel.MainViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(context) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(context) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}