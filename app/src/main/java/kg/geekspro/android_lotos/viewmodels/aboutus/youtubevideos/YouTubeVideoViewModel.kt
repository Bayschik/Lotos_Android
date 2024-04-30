package kg.geekspro.android_lotos.viewmodels.aboutus.youtubevideos

import androidx.lifecycle.LiveData
import kg.geekspro.android_lotos.models.aboutusmodel.youtubemodel.YouTubeVideo

class YouTubeVideoViewModel {
    private val repository = kg.geekspro.android_lotos.ui.repositories.repoaboutus.Repository()

    fun getYouTube(): LiveData<YouTubeVideo> {
        return repository.getYouTube()
    }
}