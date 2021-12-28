package com.example.myapplication.data.service

import com.example.myapplication.data.responses.MemeItemResponse
import retrofit2.Call
import retrofit2.http.GET

interface MemeService {
    @GET("/get_memes")
    fun getMemeList(): Call<MemeItemResponse>
}