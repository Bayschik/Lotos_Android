import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.youtubeinterface.YouTubeApiService
import java.io.IOException

class YouTubePagingSource(private val apiService: YouTubeApiService) : PagingSource<Int, Result1>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result1> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getYouTube(page).execute()
            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()

                Log.d("YouTubePagingSource", "Loaded data for page: $page, count: ${data.size}")

                LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = page + 1
                )
            } else {
                val errorMessage = "Error loading data: ${response.code()} ${response.message()}"
                Log.e("YouTubePagingSource", errorMessage)
                LoadResult.Error(IOException(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("YouTubePagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result1>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
