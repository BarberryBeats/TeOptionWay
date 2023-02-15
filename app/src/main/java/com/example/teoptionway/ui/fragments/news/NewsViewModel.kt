package com.example.teoptionway.ui.fragments.news

import android.app.Application
import androidx.lifecycle.*
import com.example.core.common.JsonReader
import com.example.teoptionway.App
import com.example.teoptionway.data.model.News
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsViewModel(app: Application) : AndroidViewModel(app) {
    private val jsonReader = JsonReader()
    private val _url = MutableStateFlow("")
    val url = _url.asStateFlow()

    fun jsonToGson(json: String): LiveData<List<News>> {
        val jsonFileString = jsonReader.getJsonDataFromAsset(getApplication(), json)
        val listResult: List<News> =
            Gson().fromJson(jsonFileString, Array<News>::class.java).toList()
        val listLiveData = MutableLiveData<List<News>>()
        listLiveData.value = listResult
        return listLiveData
    }

    fun getUrl() {
        viewModelScope.launch {
            App.repository.getRemoteConfig().collectLatest {
                _url.value = it
            }
        }
    }
}