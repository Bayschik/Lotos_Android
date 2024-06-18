package kg.geekspro.android_lotos.ui.repositories.repoaboutus

import YouTubePagingSource
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.youtubeinterface.YouTubeApiService
import javax.inject.Inject

class YouTubeRepositoryImpl @Inject constructor(
    private val apiService: YouTubeApiService
) {
    fun getPaginatedYouTubeData(): LiveData<PagingData<Result1>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { YouTubePagingSource(apiService) }
        ).liveData
    }
}
