package respo.app.nost

import android.app.Application
import respo.app.nost.repositories.NewsRepository

class App : Application() {
    companion object {
        val repository: NewsRepository by lazy {
            NewsRepository()
        }
    }
}