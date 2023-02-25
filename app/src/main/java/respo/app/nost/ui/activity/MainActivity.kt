package respo.app.nost.ui.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import respo.app.nost.R
import respo.app.nost.databinding.ActivityMainBinding
import respo.app.nost.ui.fragments.news.NewsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}