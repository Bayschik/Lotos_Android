package kg.geekspro.android_lotos.ui.interfaces.fcmtoken

import kg.geekspro.android_lotos.models.firebasetoken.FcmAnswer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FcmApiService {

    @POST("api/v1/client_create/set_token/")
    fun getFcm(
        @Header("Authorization") accessToken:String,
        @Body fcmToken: FcmAnswer
    ): Call<FcmAnswer>


}