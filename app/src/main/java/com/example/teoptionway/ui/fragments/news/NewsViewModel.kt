package com.example.teoptionway.ui.fragments.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.common.JsonReader
import com.example.teoptionway.data.model.News
import com.example.teoptionway.repositories.NewsRepository
import com.example.teoptionway.utils.RemoteConfig
import com.google.gson.Gson

class NewsViewModel(app: Application): AndroidViewModel(app) {
    private val newsRepository = NewsRepository()
    private val jsonReader = JsonReader()
    private lateinit var newsList: List<News>
    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>>
    get() = _news
    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
    get() = _url


    fun jsonToGson(json: String) {
        val jsonFileString = jsonReader.getJsonDataFromAsset(getApplication(), json)
        val listResult: List<News> = Gson().fromJson(jsonFileString, Array<News>::class.java).toList()
        newsList = listResult
    }


    fun getNewsList() {
        _news.value = newsList

    }

    fun getUrl(){
        Log.d("Ray", "config ${newsRepository.getRemoteConfig().value}")
        _url.value = newsRepository.getRemoteConfig().value
    }


}