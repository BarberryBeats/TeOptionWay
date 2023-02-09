package com.example.teoptionway.ui.news_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teoptionway.databinding.ActivityMainBinding
import com.example.teoptionway.model.News


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        observeViewModel()

    }

    private fun initAdapter(newsList: List<News>) {
        adapter = NewsAdapter(newsList)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvNews.layoutManager = llm
        binding.rvNews.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.jsonToGson("News.json")
    }

    private fun observeViewModel() {
        viewModel.getUserInfo().observe(this) {
                       initAdapter(it)
        }
    }
}