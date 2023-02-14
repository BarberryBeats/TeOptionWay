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
import kotlinx.coroutines.GlobalScope

class NewsViewModel(app: Application): AndroidViewModel(app) {

    private val newsRepository = NewsRepository()
    private val jsonReader = JsonReader()


    fun jsonToGson(json: String): LiveData<List<News>> {
        val jsonFileString = jsonReader.getJsonDataFromAsset(getApplication(), json)
        val listResult: List<News> = Gson().fromJson(jsonFileString, Array<News>::class.java).toList()
        val listLiveData = MutableLiveData<List<News>>()
        listLiveData.value = listResult
        return listLiveData
    }

   fun getUrl(): LiveData<String>{

        return newsRepository.getRemoteConfig()
    }


}