package kg.geekspro.android_lotos.ui.adapters.aboutusadapter.youtubeadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ItemYoutubeVideoBinding
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1

class YoutubeAdapter : PagingDataAdapter<Result1, YoutubeAdapter.YouTubeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_youtube_video, parent, false)
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
                btnYouTubeVideo1.setVideoPath(result.url)
                btnYouTubeVideo1.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
                tvTitleYouTube.text = result.title
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Result1>() {
            override fun areItemsTheSame(oldItem: Result1, newItem: Result1): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result1, newItem: Result1): Boolean {
                return oldItem == newItem
            }
        }
    }
}
