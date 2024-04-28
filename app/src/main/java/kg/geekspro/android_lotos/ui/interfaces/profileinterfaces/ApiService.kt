package kg.geekspro.android_lotos.ui.interfaces.profileinterfaces

import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    //@Headers("Content-Type: application/json")
    @POST("api/v1/client_create/")
    fun verifyEmail(
        @Body email: Registration
    ):Call<String>

    @POST("api/v1/client_create/confirm/")
    fun confirmCode(
        @Header("Cookie") sessionId:String,
        @Body code: VerificationCode
    ):Call<String>

    @POST("api/v1/client_create/set_data/")
    fun clientCreate(
        @Body code: PersonalData,
        @Header("Cookie") sessionId:String
    ):Call<String>

    @POST("api/v1/client_create/set_password/")
    fun setPassword(
        @Body password: Password,
        @Header("Cookie") sessionId:String
    ):Call<PasswordCreate>

    @GET("api/v1/profile/")
    fun getProfile():Call<Profile>
}