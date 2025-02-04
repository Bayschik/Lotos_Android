package kg.geekspro.android_lotos.ui.adapters.aboutusadapter.youtubeadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ItemYoutubeVideoBinding
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1

class YoutubeAdapter(private val itemClickListener: (Result1) -> Unit) :
    PagingDataAdapter<Result1, YoutubeAdapter.YouTubeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_youtube_video, parent, false)
        return YouTubeViewHolder(view)
    }

    override fun onBindViewHolder(holder: YouTubeViewHolder, position: Int) {
        val result = getItem(position)
        result?.let { holder.bind(it) }
    }

    inner class YouTubeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemYoutubeVideoBinding.bind(itemView)

        fun bind(result: Result1) {
            binding.apply {
                tvTitleYouTube.text = result.title
            }
            itemView.setOnClickListener {
                itemClickListener(result)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Result1>() {
            override fun areItemsTheSame(oldItem: Result1, newItem: Result1): Boolean {
                // Implement a logic to check whether items are the same.
                // For example, if they have unique IDs:
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result1, newItem: Result1): Boolean {
                // Implement a logic to check whether contents of the items are the same.
                return oldItem == newItem
            }
        }
    }
}