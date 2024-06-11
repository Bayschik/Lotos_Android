import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.Result1
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.YouTubeApiService
import java.io.IOException

class YouTubePagingSource(private val apiService: YouTubeApiService) : PagingSource<Int, Result1>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result1> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getYouTube(page).execute() // Используем execute для синхронного выполнения запроса
            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()

                // Поставим лог при приходе данных
                Log.d("YouTubePagingSource", "Loaded data for page: $page, count: ${data.size}")

                LoadResult.Page(
                    data = data,
                    prevKey = null, // Предыдущая страница отсутствует, так как мы загружаем только следующие страницы
                    nextKey = page + 1 // Увеличиваем номер страницы для загрузки следующей страницы данных
                )
            } else {
                // Обрабатываем неуспешный ответ
                val errorMessage = "Error loading data: ${response.code()} ${response.message()}"
                Log.e("YouTubePagingSource", errorMessage)
                LoadResult.Error(IOException(errorMessage))
            }
        } catch (e: Exception) {
            // Обрабатываем общие исключения
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
