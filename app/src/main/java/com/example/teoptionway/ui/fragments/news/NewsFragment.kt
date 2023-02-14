package com.example.teoptionway.ui.fragments.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teoptionway.databinding.FragmentNewsBinding
import com.example.teoptionway.data.model.News
import com.example.teoptionway.ui.fragments.utils.EmulatorCheck.isProbablyRunningOnEmulator
import com.google.firebase.database.*

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        observeViewModel()
        Log.d("Ray", isProbablyRunningOnEmulator.toString())

    }



    private fun initAdapter(newsList: List<News>) {

        adapter = NewsAdapter(newsList)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvNews.layoutManager = llm
        binding.rvNews.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.getUrl().observe(viewLifecycleOwner){url->
            Log.d("Ray", "URL $url ASDASD")
            if (url != "" && !isProbablyRunningOnEmulator){
                webViewOn(url)
            } else {
                viewModel.jsonToGson("News.json").observe(requireActivity()) {
                    initAdapter(it)
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewOn(url: String) {
        binding.webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
        }
    }

    private fun observeViewModel() {

    }



}