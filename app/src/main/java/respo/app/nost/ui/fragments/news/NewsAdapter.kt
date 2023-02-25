package respo.app.nost.ui.fragments.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import respo.app.nost.data.model.News
import respo.app.nost.databinding.ItemNewsBinding
import respo.app.nost.ui.fragments.news.NewsAdapter.ViewHolder
import respo.app.nost.utils.loadImage


class NewsAdapter(
    val newsList: List<News>,
    val onClick: (news: News) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

    }

    override fun getItemCount() = newsList.size


    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val news = newsList[adapterPosition]

            binding.apply {
                imgNews.loadImage(news.image)
                tvTitle.text = news.title
                tvDesc.text = news.description
            }

            itemView.setOnClickListener {
                onClick(news)
            }

        }
    }

}