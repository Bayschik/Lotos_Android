package kg.geekspro.android_lotos.presentation.ui.data

import kg.geekspro.android_lotos.presentation.ui.fragments.verificationCode.VerificationCode
import kg.geekspro.android_lotos.presentation.ui.fragments.registration.Registration
import kg.geekspro.android_lotos.presentation.ui.fillData.PersonalData
import kg.geekspro.android_lotos.presentation.ui.profile.Profile
import kg.geekspro.android_lotos.presentation.ui.profile.password.create.Password
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