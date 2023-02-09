package com.example.teoptionway.ui.news_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teoptionway.model.News
import com.example.teoptionway.utils.JsonReader
import com.google.gson.Gson

class NewsViewModel(app: Application): AndroidViewModel(app) {
    private val jsonReader = JsonReader()
    private lateinit var news: List<News>
    private val data = MutableLiveData<List<News>>()

    fun jsonToGson(json: String) {
        val jsonFileString = jsonReader.getJsonDataFromAsset(getApplication(), json)
        val listResult: List<News> = Gson().fromJson(jsonFileString, Array<News>::class.java).toList()
        news = listResult
    }


    fun getUserInfo(): LiveData<List<News>> {
        data.value = news
        return data
    }
}