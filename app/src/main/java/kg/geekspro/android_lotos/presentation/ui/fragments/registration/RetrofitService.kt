package kg.geekspro.android_lotos.presentation.ui.fragments.registration

import kg.geekspro.android_lotos.presentation.ui.data.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val retrofit = Retrofit.Builder().baseUrl("http://209.38.228.54:88/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    val api = retrofit.create(ApiService::class.java)
}