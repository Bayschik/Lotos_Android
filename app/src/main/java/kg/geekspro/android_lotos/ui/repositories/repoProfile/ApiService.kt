package kg.geekspro.android_lotos.ui.repositories.repoProfile

import kg.geekspro.android_lotos.models.verifyCode.VerificationCode
import kg.geekspro.android_lotos.models.registrationModel.Registration
import kg.geekspro.android_lotos.models.orderHistoryModel.PersonalData
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.profile.Password
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/verify_email/")
    fun verifyEmail(@Body email: Registration):Call<String>

    @POST("api/v1/confirm_code/")
    fun confirmCode(@Body code: VerificationCode):Call<String>

    @POST("api/v1/client_create/")
    fun clientCreate(@Body code: PersonalData):Call<String>

    @POST("api/v1/set_password/")
    fun setPassword(@Body password: Password):Call<String>

    @GET("api/v1/profile/")
    fun getProfile():Call<Profile>
}