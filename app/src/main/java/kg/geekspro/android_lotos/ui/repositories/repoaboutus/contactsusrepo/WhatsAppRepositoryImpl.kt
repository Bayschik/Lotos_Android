package kg.geekspro.android_lotos.ui.repositories.repoaboutus.contactsusrepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.models.aboutusmodels.contactsmodels.WhatsAppModel
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.contactsinterface.WhatsAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WhatsAppRepositoryImpl @Inject constructor(
    private val apiService: WhatsAppInterface
)  {

    fun loadWP(): LiveData<List<WhatsAppModel>> {

        val fcmAnswer = MutableLiveData<List<WhatsAppModel>>()

        apiService.getWhatsApp().enqueue(object :
            Callback<List<WhatsAppModel>> {
            override fun onResponse(call: Call<List<WhatsAppModel>>, response: Response<List<WhatsAppModel>>) {
                if (response.isSuccessful) {
                    fcmAnswer.value = response.body()
                    Log.d("FcmRepository", "Token sent successfully: ${response.body()}")
                } else {
                    Log.e("FcmRepository", "Failed to send token: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<WhatsAppModel>>, t: Throwable) {
                Log.e("FcmRepository", "Error sending token", t)
            }
        })

        return fcmAnswer
    }
}




/*
class YouTubeRepositoryImpl @Inject constructor(
    private val apiService: YouTubeApiService
) {

    fun getVideos() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { YouTubePagingSource(apiService) }
    ).liveData
}
 */