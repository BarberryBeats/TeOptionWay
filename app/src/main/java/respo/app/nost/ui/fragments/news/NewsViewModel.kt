package respo.app.nost.ui.fragments.news

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import respo.app.nost.App
import respo.app.nost.data.model.News
import respo.example.core.common.JsonReader

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

    fun getUrl(): LiveData<String> {
       return App.repository.getRemoteUrlResult()
    }
}