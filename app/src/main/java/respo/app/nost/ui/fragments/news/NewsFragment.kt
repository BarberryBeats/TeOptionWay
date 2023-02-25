package respo.app.nost.ui.fragments.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import respo.app.nost.R
import respo.app.nost.data.model.News
import respo.app.nost.databinding.FragmentNewsBinding
import respo.app.nost.ui.fragments.utils.EmulatorCheck.isProbablyRunningOnEmulator
import respo.example.core.common.InternetConnectivityManager
import respo.example.core.data.local.StoragePreferences

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val viewModel by viewModels<NewsViewModel>()
    private lateinit var adapter: NewsAdapter
    private lateinit var storagePreferences: StoragePreferences
    private lateinit var internetConnectCheck: InternetConnectivityManager

    companion object{
        const val KEY_FOR_NEWS = "NEWS"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        if (isProbablyRunningOnEmulator) {
            viewModel.jsonToGson("News.json").observe(requireActivity()) {
                binding.webView.visibility = GONE
                binding.rvNews.visibility = VISIBLE

                Log.d("Ray", it.toString())
                initAdapter(it)

            }
        } else {
            internetConnectCheck.observe(viewLifecycleOwner) { internetOn ->
                if (internetOn) {

                    if (storagePreferences.url.isNullOrEmpty()) {

                        observeViewModel()
                    } else {
                        Log.d("Ray", "else")
                        binding.webView.visibility = VISIBLE
                        binding.rvNews.visibility = GONE
                        webViewOn(storagePreferences.url!!)
                    }
                } else {
                    Log.d("Ray", "инета нет")
                }
            }
        }
    }


    private fun initView() {
        storagePreferences = StoragePreferences(requireContext())
        internetConnectCheck = InternetConnectivityManager(requireContext())
    }


    private fun initAdapter(newsList: List<News>) {

        adapter = NewsAdapter(newsList, this::onClick)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvNews.layoutManager = llm
        binding.rvNews.adapter = adapter
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewOn(url: String) {
        binding.webView.apply {
            Log.d("Ray", "webview $url")
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }
            }
            loadUrl(url)
        }
    }


    private fun observeViewModel() {

        viewModel.getUrl().observe(viewLifecycleOwner) { url ->
            if (url.isNotEmpty() && !isProbablyRunningOnEmulator) {
                storagePreferences.url = url
                Log.d("Ray", "a")

                Log.d("Ray", url)
                binding.webView.visibility = VISIBLE
                binding.rvNews.visibility = GONE
                webViewOn(url)
            } else {
                Log.d("Ray", "asdasd")
                binding.webView.visibility = GONE
                binding.rvNews.visibility = VISIBLE
                viewModel.jsonToGson("News.json").observe(requireActivity()) {
                    initAdapter(it)
                }
            }
        }
    }
    private fun onClick(news: News) {
        findNavController().navigate(R.id.newsDetailFragment, bundleOf(KEY_FOR_NEWS to news))
    }

}


