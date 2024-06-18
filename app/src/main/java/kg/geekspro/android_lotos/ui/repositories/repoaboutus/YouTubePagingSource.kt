import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.youtubeinterface.YouTubeApiService
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class YouTubePagingSource(private val apiService: YouTubeApiService) : PagingSource<Int, Result1>() {

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun getRefreshKey(state: PagingState<Int, Result1>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result1> {
        return try {
            val page = params.key ?: 1
            var loadResult: LoadResult<Int, Result1>? = null

            executor.execute {
                try {
                    val response = apiService.getYouTube(page).execute()

                    if (response.isSuccessful) {
                        val data = response.body()?.results ?: emptyList()

                        Log.d("YouTubePagingSource", "Loaded data for page: $page, count: ${data.size}")
                        data.forEach { video ->
                            Log.d("YouTubePagingSource", "Video loaded: Title=${video.title}, URL=${video.url}")
                        }
                        loadResult = LoadResult.Page(
                            data = data,
                            prevKey = null,
                            nextKey = page + 1
                        )
                    } else {
                        val errorMessage = "Error loading data: ${response.code()} ${response.message()}"
                        Log.e("YouTubePagingSource", errorMessage)
                        loadResult = LoadResult.Error(IOException(errorMessage))
                    }
                } catch (e: IOException) {
                    Log.e("YouTubePagingSource", "Error loading data", e)
                    loadResult = LoadResult.Error(e)
                } catch (e: Exception) {
                    Log.e("YouTubePagingSource", "Unexpected error", e)
                    loadResult = LoadResult.Error(e)
                }
            }

            // Ждем завершения выполнения задачи в фоновом потоке
            while (loadResult == null) {
                Thread.sleep(100)
            }

            loadResult ?: LoadResult.Error(IOException("Unknown error"))

        } catch (e: Exception) {
            Log.e("YouTubePagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }
}
