package respo.app.nost.ui.fragments.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    private var data = ""


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
        internetConnectCheck.observe(viewLifecycleOwner) { internetOn ->
            if (internetOn) {
                if (storagePreferences.url.isNullOrEmpty()) {
                    observeViewModel()
                } else {
                    webViewOn(storagePreferences.url!!)
                }
            } else {
                Log.d("Ray", "инета нет")
            }
        }
    }

    private fun initView() {
        storagePreferences = StoragePreferences(requireContext())
        internetConnectCheck = InternetConnectivityManager(requireContext())
    }


    private fun initAdapter(newsList: List<News>) {

        adapter = NewsAdapter(newsList)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvNews.layoutManager = llm
        binding.rvNews.adapter = adapter
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewOn(url: String) {
        binding.webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
        }
    }

    private fun observeViewModel() {
        viewModel.getUrl().observe(viewLifecycleOwner) { url ->
            Log.d("Ray", isProbablyRunningOnEmulator.toString())
            if (url != "" && !isProbablyRunningOnEmulator) {
                storagePreferences.url = url
                webViewOn(url)
            } else {
                viewModel.jsonToGson("News.json").observe(requireActivity()) {
                    initAdapter(it)
                }
            }
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.url.collectLatest { url ->
                Log.d("Ray", isProbablyRunningOnEmulator.toString())
                if (url != "" && !isProbablyRunningOnEmulator) {
                    storagePreferences.url = url
                    webViewOn(url)
                } else {
                    viewModel.jsonToGson("News.json").observe(requireActivity()) {
                        initAdapter(it)
                    }
                }
            }
        }
    }
}