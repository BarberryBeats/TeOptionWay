package respo.app.nost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import respo.app.nost.data.model.News
import respo.app.nost.databinding.FragmentNewsDetailBinding
import respo.app.nost.ui.fragments.news.NewsFragment.Companion.KEY_FOR_NEWS
import respo.app.nost.utils.loadImage

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
    }

    private fun getArgument() {
        arguments?.apply {
            val news = getParcelable<News>(KEY_FOR_NEWS)
            binding.apply {
                tvNews.text = news?.title
                tvDesc.text = news?.description
                imgNews.loadImage(news?.image.toString())
            }
        }


    }

}