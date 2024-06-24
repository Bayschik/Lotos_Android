package kg.geekspro.android_lotos.ui.fragments.aboutus

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kg.geekspro.android_lotos.databinding.FragmentPlayBinding


class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private val videoUrl: String? by lazy { arguments?.getString("url") }
    private val videoId: String by lazy { extractVideoIdFromUrl(videoUrl ?: "") }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVideo()
        val url = arguments?.getString("url")
        val id = arguments?.getString("id")
    }
    fun extractVideoIdFromUrl(videoUrl: String): String {
        val videoIdKey = "v="
        val startIndex = videoUrl.indexOf(videoIdKey)
        if (startIndex != -1) {
            val endIndex = videoUrl.indexOf("&", startIndex)
            return if (endIndex != -1) {
                videoUrl.substring(startIndex + videoIdKey.length, endIndex)
            } else {
                videoUrl.substring(startIndex + videoIdKey.length)
            }
        } else {
            val startIndex = videoUrl.lastIndexOf("/") + 1
            val endIndex = videoUrl.indexOf("?", startIndex)
            return if (endIndex != -1) {
                videoUrl.substring(startIndex, endIndex)
            } else {
                videoUrl.substring(startIndex)
            }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun initVideo(){

        Log.d("PlayActivity", "YoutubePlayerInit: $videoUrl")
        lifecycle.addObserver(binding.playerView)

        binding.playerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
            }
        })

    }

}


/*override fun checkInternetConnection() {
    super.checkInternetConnection()
    ConnectionLiveData(application).observe(this) { isConnection ->
        if (!isConnection) {
            binding.mainContainer.visibility = View.GONE
            binding.noConnection.visibility = View.VISIBLE
        }
        binding.noInternetConnectionInclude.btnTryAgain.setOnClickListener {
            if (!isConnection) {
                binding.mainContainer.visibility = View.GONE
                binding.noConnection.visibility = View.VISIBLE
            } else {
                binding.mainContainer.visibility = View.VISIBLE
                binding.noConnection.visibility = View.GONE
            }
        }
    }
}*/