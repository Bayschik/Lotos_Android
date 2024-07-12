package kg.geekspro.android_lotos.ui.interfaces.maininterfeces

import kg.geekspro.android_lotos.models.mainmodels.ActionsModel
import kg.geekspro.android_lotos.models.mainmodels.MainEntities
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApiService {
    @GET("api/v1/category/")
    fun getMain(
        @Header("Authorization") accessToken:String,
    ): Call<MainEntities>

    @GET("api/v1/actions/")
    fun getActions(): Call<ActionsModel.Result>
}