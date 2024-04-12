package kg.geekspro.android_lotos.presentation.ui.data

import kg.geekspro.android_lotos.presentation.ui.fragments.VerificationCode
import kg.geekspro.android_lotos.presentation.ui.model.PersonalData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/verify_email/")
    fun verifyEmail(@Body email:String):Call<String>

    @POST("api/v1/confirm_code/")
    fun confirmCode(@Body code:VerificationCode):Call<String>

    @POST("api/v1/client_create/")
    fun clientCreate(@Body code:PersonalData):Call<String>
}