package com.example.myapplication.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    fun provideImgFlipService() = Retrofit.Builder()
        .baseUrl("https://api.imgflip.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun provideMemeService() = provideImgFlipService().create(MemeService::class.java)
}