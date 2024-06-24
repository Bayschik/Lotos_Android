package kg.geekspro.android_lotos.viewmodels.aboutus.youtubevideos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1
import kg.geekspro.android_lotos.ui.repositories.repoaboutus.youtuberepo.YouTubeRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: YouTubeRepositoryImpl
) : ViewModel() {


    fun loadVideo(): LiveData<PagingData<Result1>> {
        return repository.getVideos().cachedIn(viewModelScope)
    }
}