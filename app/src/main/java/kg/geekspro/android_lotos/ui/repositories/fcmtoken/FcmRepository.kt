package kg.geekspro.android_lotos.ui.repositories.fcmtoken

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.models.firebasetoken.FcmAnswer
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.interfaces.fcmtoken.FcmApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FcmRepository @Inject constructor(private val apiService: FcmApiService,
                                        private val pref: Pref ) {

    fun sendFcmToken(fcmToken: String): LiveData<FcmAnswer> {
        val token = pref.getAccessToken()
        val fcmAnswer = MutableLiveData<FcmAnswer>()

        apiService.getFcm("Bearer $token", FcmAnswer(fcmToken)).enqueue(object : Callback<FcmAnswer> {
            override fun onResponse(call: Call<FcmAnswer>, response: Response<FcmAnswer>) {
                if (response.isSuccessful) {
                    fcmAnswer.value = response.body()
                    Log.d("FcmRepository", "Token sent successfully: ${response.body()}")
                } else {
                    Log.e("FcmRepository", "Failed to send token: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<FcmAnswer>, t: Throwable) {
                Log.e("FcmRepository", "Error sending token", t)
            }
        })

        return fcmAnswer
    }

}