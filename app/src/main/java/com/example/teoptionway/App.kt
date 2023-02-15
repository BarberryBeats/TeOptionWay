package com.example.teoptionway

import android.app.Application
import com.example.teoptionway.repositories.NewsRepository

class App : Application() {
    companion object {
        val repository: NewsRepository by lazy {
            NewsRepository()
        }
    }
}