package com.example.myapplication

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val image = intent.getParcelableExtra<Uri>(EXTRA_IMAGE)
        if (image == null) Toast.makeText(this, "Gagal mengambil gambar meme", Toast.LENGTH_SHORT)
            .show()

        Glide.with(this).load(image).into(binding.imgShare)
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
    }
}