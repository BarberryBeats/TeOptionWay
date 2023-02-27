package respo.app.nost.ui.fragments.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.View.*
import android.webkit.CookieManager
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
    private var webViewState: Bundle? = null
    private var onWebViewOn = false


    companion object {
        const val KEY_FOR_NEWS = "NEWS"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webViewState = Bundle()
        binding.webView.saveState(webViewState!!)
        outState.putBundle("webViewState", webViewState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            webViewState = savedInstanceState.getBundle("webViewState")
            if (webViewState != null) {
                binding.webView.restoreState(webViewState!!)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        checkForUserOrEmulatorJoin()
    }

    private fun checkForUserOrEmulatorJoin() {
        binding.noInternetCheckLayout.visibility = GONE

        if (isProbablyRunningOnEmulator) {
            viewModel.jsonToGson("News.json").observe(requireActivity()) {
                binding.webView.visibility = GONE
                binding.rvNews.visibility = VISIBLE

                initAdapter(it)

            }
        } else {
            if (storagePreferences.url.isNullOrEmpty()) {
                observeViewModel()
            } else {
                binding.webView.visibility = VISIBLE
                binding.rvNews.visibility = GONE
                webViewOn(storagePreferences.url!!)
                internetConnectCheck.observe(viewLifecycleOwner) { internetOn ->

                    if (internetOn) {
                        binding.webView.visibility = VISIBLE
                        binding.noInternetCheckLayout.visibility = GONE

                    } else {

                        binding.rvNews.visibility = GONE
                        binding.webView.visibility = GONE
                        binding.noInternetCheckLayout.visibility = VISIBLE
                    }
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
            onWebViewOn = true
            settings.domStorageEnabled = true
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            val mWebSettings = this.settings
            settings.javaScriptEnabled = true
            mWebSettings.loadWithOverviewMode = true
            mWebSettings.useWideViewPort = true
            mWebSettings.databaseEnabled = true
            mWebSettings.setSupportZoom(false)
            mWebSettings.allowFileAccess = true
            mWebSettings.allowContentAccess = true
            mWebSettings.loadWithOverviewMode = true
            mWebSettings.useWideViewPort = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }
            }
            binding.webView.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && binding.webView.canGoBack()) {
                    binding.webView.goBack()
                    true
                } else url == "https://ya.ru"
            }
            loadUrl(url)
        }
    }


    private fun observeViewModel() {

        viewModel.getUrl().observe(viewLifecycleOwner) { url ->
            /*if (url == "offInternet"){
                binding.apply {
                    binding.rvNews.visibility = GONE
                    binding.webView.visibility = GONE
                    binding.noInternetCheckLayout.visibility = VISIBLE
                    internetConnectCheck.observe(viewLifecycleOwner){
                        observeViewModel()
                    }
                }
            }*/
            if (url.isNotEmpty() && !isProbablyRunningOnEmulator) {
                storagePreferences.url = url

                binding.webView.visibility = VISIBLE
                binding.rvNews.visibility = GONE
                webViewOn(url)
            } else {
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


