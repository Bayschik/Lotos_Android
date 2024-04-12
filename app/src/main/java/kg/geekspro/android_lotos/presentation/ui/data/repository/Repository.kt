package kg.geekspro.android_lotos.presentation.ui.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.presentation.ui.data.ApiService
import kg.geekspro.android_lotos.presentation.ui.fragments.Code
import kg.geekspro.android_lotos.presentation.ui.fragments.VerificationCode
import kg.geekspro.android_lotos.presentation.ui.fragments.registration.Registration
import kg.geekspro.android_lotos.presentation.ui.fragments.registration.RetrofitService
import kg.geekspro.android_lotos.presentation.ui.model.PersonalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val api = RetrofitService.api
    fun verifyEmail(emaill:String):LiveData<String>{
        val email = MutableLiveData<String>()

        api.verifyEmail(emaill).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        email.postValue(it)
                        Log.d("onSuccess", "${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("emailOnFailure", "${t.message}")
            }

        })
        //email.postValue(api.verifyEmail(emaill).body())
        return email
    }

    fun confirmCode(code:VerificationCode):LiveData<String>{
        val codde = MutableLiveData<String>()

        api.confirmCode(code).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        codde.postValue(it)
                        Log.d("onSuccessString", "${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("codeOnFailure", "${t.message}")
            }

        })
        //codde.postValue(api.verifyEmail(coddel).body())
        return codde
    }

    fun clientCreate(data: PersonalData):LiveData<String>{
        val client = MutableLiveData<String>()

        api.clientCreate(data).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        client.postValue(it)
                        Log.d("onSuccessString", "${response.body()}")
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("codeOnFailure", "${t.message}")
            }

        })
        //client.postValue(api.verifyEmail(clientl).body())
        return client
    }
}