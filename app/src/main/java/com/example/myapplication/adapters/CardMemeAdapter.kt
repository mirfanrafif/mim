package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.DetailActivity
import com.example.myapplication.data.responses.Meme
import com.example.myapplication.databinding.CardMemeImageBinding

class CardMemeAdapter(private val context: Context) : RecyclerView.Adapter<CardMemeAdapter.CardMemeViewHolder>() {
    var memeList = ArrayList<Meme>()

    fun setData(memeList: List<Meme>) {
        this.memeList.addAll(memeList)
        notifyDataSetChanged()
    }

    inner class CardMemeViewHolder(private val binding: CardMemeImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meme: Meme) {
            with(binding) {
                Glide.with(root).load(meme.url).into(imgMeme)
                memeCard.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MEME, meme)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMemeViewHolder {
        val binding = CardMemeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardMemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardMemeViewHolder, position: Int) {
        holder.bind(memeList[position])
    }

    override fun getItemCount(): Int = memeList.size
}