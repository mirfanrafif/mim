package com.example.myapplication.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.responses.Meme
import com.example.myapplication.data.responses.MemeItemResponse
import com.example.myapplication.data.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val context: Context): ViewModel() {
    fun getMemeList(): LiveData<List<Meme>> {
        val listMemeLiveData = MutableLiveData<List<Meme>>()
        ApiService().provideMemeService().getMemeList()
            .enqueue(object : Callback<MemeItemResponse> {
                override fun onResponse(
                    call: Call<MemeItemResponse>,
                    response: Response<MemeItemResponse>
                ) {
                    if (!response.isSuccessful){
                        Toast.makeText(context, "Gagal mengambil gambar meme", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val data = response.body()
                        if(data == null) {
                            Toast.makeText(context, "Data kosong", Toast.LENGTH_SHORT)
                                .show()
                        }else if(!data.success){
                            Toast.makeText(context, "Gagal mengambil gambar meme", Toast.LENGTH_SHORT)
                                .show()
                        }else if(data.data.memes.isEmpty()) {
                            Toast.makeText(context, "List meme kosong", Toast.LENGTH_SHORT)
                                .show()
                        }else{
                            listMemeLiveData.postValue(data.data.memes)
                        }
                    }

                }

                override fun onFailure(call: Call<MemeItemResponse>, t: Throwable) {
                    Toast.makeText(context, "Gagal mengambil gambar meme", Toast.LENGTH_SHORT)
                        .show()
                    t.printStackTrace()
                }

            })

        return listMemeLiveData
    }
}