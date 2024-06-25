package kg.geekspro.android_lotos.ui.interfaces.profileinterfaces

import kg.geekspro.android_lotos.ReviewModel
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.ui.fragments.login.LogIn
import kg.geekspro.android_lotos.ui.fragments.profile.Token
import kg.geekspro.android_lotos.ui.fragments.profile.TokenVerify
import kg.geekspro.android_lotos.ui.fragments.profile.logOut.LogOutMessage
import kg.geekspro.android_lotos.ui.fragments.profile.logOut.RefreshToken
import kg.geekspro.android_lotos.ui.fragments.profile.order.Order
import kg.geekspro.android_lotos.ui.fragments.profile.order.OrderList
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.fragments.registration.Model
import kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail.ChangeEmail
import kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail.Code
import kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword.ChangePassword
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/v1/client_create/")
    fun verifyEmail(
        @Body email: Registration
    ):Call<String>

    @GET("accounts/google/login/")
    fun googleAuth(
        @Query("process") process:String = "login"
    ):Call<Unit>

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

    @POST("api/v1/auth/jwt/create/")
    fun logIn(
        @Body logIn:LogIn,
    ):Call<PasswordCreate>

    @GET("api/v1/profile/")
    fun getProfile(
        @Header("Authorization") accessToken:String
    ):Call<Profile>

    @GET("api/v1/order/")
    fun getOrderList(
        @Header("Authorization") accessToken:String
    ):Call<List<OrderList.OrderListItem>>

    @GET("api/v1/order/{id}/")
    fun getOrderId(
        @Path("id") id:Int,
        @Header("Authorization") accessToken:String
    ):Call<Order>


    @Multipart
    @PUT("api/v1/profile/")
    fun putProfile(
        @Part photo:MultipartBody.Part,
        @Part("first_name") firstName:RequestBody,
        @Part("last_name") lastName:RequestBody,
        @Part("date_of_birth") dateOfBirth:RequestBody,
        @Part("address") address:RequestBody,
        @Header("Authorization") accessToken:String
    ):Call<Profile>

    @POST("api/v1/logout/")
    fun logOut(
        @Header("Authorization") accessToken:String,
        @Body refreshToken:RefreshToken
    ):Call<LogOutMessage>

    @POST("api/v1/change_email/")
    fun changeEmail(
        @Header("Authorization") accessToken:String,
        @Body changeEmail: ChangeEmail
    ):Call<Any>

    @POST("api/v1/change_email/confirm/")
    fun changeEmailConfirm(
        @Header("Authorization") accessToken:String,
        @Header("Cookie") sessionId:String,
        @Body code:Code
    ):Call<String>

    @POST("api/v1/change_password/")
    fun changePassword(
        @Header("Authorization") accessToken:String,
        @Body changePassword: ChangePassword
    ):Call<Unit>

    @POST("api/v1/auth/jwt/verify/")
    fun checkUser(
        @Body accessToken:Token,
    ):Call<TokenVerify>

    @POST("api/v1/auth/jwt/refresh/")
    fun refreshToken(
        @Body refreshToken:kg.geekspro.android_lotos.ui.fragments.profile.RefreshToken,
    ):Call<PasswordCreate>

    @Multipart
    @POST("api/v1/review/create/")
    fun leaveReview(
        @Part images:List<MultipartBody.Part>,
        @Part("text") text:RequestBody,
        @Part("stars") stars:RequestBody,
        @Part("order_id") orderId:RequestBody,
        @Header("Authorization") accessToken:String,
    ):Call<ReviewModel>

    @DELETE("api/v1/profile/")
    fun deleteAccount(
        @Header("Authorization") accessToken: String
    ):Call<Any>

}