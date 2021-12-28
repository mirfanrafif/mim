package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.data.responses.Meme
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.databinding.LayoutTextFormBinding
import com.example.myapplication.utils.ViewModelFactory
import com.example.myapplication.viewmodel.DetailViewModel
import com.vipul.hp_hp.library.Layout_to_Image
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var imageUri: Uri? = null
    private var memeText = ""
    private var memeUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val meme = intent.getParcelableExtra<Meme>(EXTRA_MEME)
        if (meme == null) {
            Toast.makeText(this, "Memenya kosong", Toast.LENGTH_SHORT).show()
            finish()
        }
        Glide.with(this).load(meme?.url).into(binding.imgMemeDisplay)

        binding.btnAddLogo.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    pilihFoto()
                }
            }else{
                pilihFoto()
            }
        }

        binding.btnAddText.setOnClickListener {
            showTextDialog()
        }

        binding.btnSave.setOnClickListener {
            var bitmap: Bitmap
            var layoutToImage = Layout_to_Image(this, binding.memeLayout)
            bitmap = layoutToImage.convert_layout()

            var anggapAjaNamaFile = ""

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    anggapAjaNamaFile = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, UUID.randomUUID().toString() , "sebuah meme");
                    Toast.makeText(this@DetailActivity,"Saved to Gallery",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                anggapAjaNamaFile = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, UUID.randomUUID().toString() , "sebuah meme");
                Toast.makeText(this@DetailActivity,"Saved to Gallery",Toast.LENGTH_SHORT).show()
            }

//            Log.d("Lokasi", anggapAjaNamaFile)
            memeUri = Uri.parse(anggapAjaNamaFile)
            checkAttr()
        }

        binding.btnShare.setOnClickListener {
            val intent = Intent(this, ShareActivity::class.java)
            intent.putExtra(ShareActivity.EXTRA_IMAGE, memeUri)
            startActivity(intent)
        }
    }

    private fun pilihFoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            binding.imgLogo.setImageURI(imageUri)
            checkAttr()
        }
    }

    private fun showTextDialog() {
        val view = LayoutTextFormBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
            .setTitle("Enter Text")
            .setView(view.root)
            .setPositiveButton("Tambah") { dialog, _ ->
                memeText = view.edtMemeText.text.toString()
                if (memeText.isBlank()) {
                    view.edtMemeText.error = "Cannot Be Empty"
                    dialog.cancel()
                }
                binding.tvMemeText.text = memeText
                checkAttr()
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }

        builder.show()
    }

    fun checkAttr() {
        if(memeUri != null) {
            binding.btnAddText.visibility = View.GONE
            binding.btnAddLogo.visibility = View.GONE
            binding.btnSave.visibility = View.GONE
            binding.btnShare.visibility = View.VISIBLE
        }else if (imageUri != null && !memeText.isBlank()) {
            binding.btnAddText.visibility = View.GONE
            binding.btnAddLogo.visibility = View.GONE
            binding.btnSave.visibility = View.VISIBLE
            binding.btnShare.visibility = View.GONE
        }else{
            binding.btnAddText.visibility = View.VISIBLE
            binding.btnAddLogo.visibility = View.VISIBLE
            binding.btnSave.visibility = View.GONE
            binding.btnShare.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_MEME = "extra_meme"
        const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE = 1001
    }
}