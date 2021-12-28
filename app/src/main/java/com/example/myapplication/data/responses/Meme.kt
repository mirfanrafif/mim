package com.example.myapplication.data.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meme(
    val box_count: Int,
    val height: Int,
    val id: String,
    val name: String,
    val url: String,
    val width: Int
): Parcelable