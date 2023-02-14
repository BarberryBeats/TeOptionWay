package com.example.teoptionway.ui.fragments.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teoptionway.databinding.ItemNewsBinding
import com.example.teoptionway.data.model.News
import com.example.teoptionway.ui.fragments.news.NewsAdapter.ViewHolder
import com.example.teoptionway.utils.loadImage


class NewsAdapter(
    val newsList: List<News>
): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = newsList.size


    inner class ViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(){
            val item = newsList[adapterPosition]

            binding.apply {
                imgNews.loadImage(item.image)
                tvTitle.text = item.title
                tvDesc.text = item.description
            }
        }
    }

}