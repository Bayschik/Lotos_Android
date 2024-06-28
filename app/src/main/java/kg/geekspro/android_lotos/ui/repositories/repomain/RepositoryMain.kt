package kg.geekspro.android_lotos.ui.repositories.repomain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.models.mainmodels.MainEntities
import kg.geekspro.android_lotos.ui.interfaces.maininterfeces.MainApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryMain @Inject constructor(private val mainApiService: MainApiService,
                                         private val pref: Pref
) {

    fun loadMain(): LiveData<MainEntities> {
        val token = pref.getAccessToken()
        val fcmAnswer = MutableLiveData<MainEntities>()

        mainApiService.getMain("Bearer $token").enqueue(object :
            Callback<MainEntities> {
            override fun onResponse(call: Call<MainEntities>, response: Response<MainEntities>) {
                if (response.isSuccessful) {
                    fcmAnswer.value = response.body()
                    Log.d("MuhlisRepository", "Token sent successfully: ${response.body()}")
                    Log.d("MuhlisRepository", "Response data: ${response.body()?.toString()}")
                } else {
                    Log.e("MuhlisRepository", "Failed to send token: ${response.errorBody()?.string()}")
                    Log.e("MuhlisRepository", "Response error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MainEntities>, t: Throwable) {
                Log.e("MuhlisRepository", "Error sending token", t)
                Log.e("MuhlisRepository", "Failure message: ${t.message}")
            }
        })

        return fcmAnswer
    }

}
