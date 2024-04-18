package kg.geekspro.android_lotos.ui.repositories.repoProfile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.models.verifyCode.VerificationCode
import kg.geekspro.android_lotos.models.registrationModel.Registration
import kg.geekspro.android_lotos.models.orderHistoryModel.PersonalData
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.profile.Password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val api = kg.geekspro.android_lotos.ui.repositories.repoProfile.RetrofitService.api
    fun verifyEmail(emailAddress: Registration):LiveData<String>{
        val email = MutableLiveData<String>()

        api.verifyEmail(emailAddress).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        email.postValue(it)
                        Log.d("onSuccessEmail", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onEmailFailure", t.toString())
            }

        })
        return email
    }

    fun confirmCode(code: VerificationCode):LiveData<String>{
        val codde = MutableLiveData<String>()

        api.confirmCode(code).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        codde.postValue(it)
                        Log.d("onSuccessCode", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onCodeFailure", t.message.toString())
            }

        })
        return codde
    }

    fun clientCreate(data: PersonalData):LiveData<String>{
        val client = MutableLiveData<String>()
        api.clientCreate(data).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        client.postValue(it)
                        Log.d("onSuccessCreate", it.toString())
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onCreateFailure", t.message.toString())
            }
        })
        return client
    }

    fun setPassword(password: Password):LiveData<String>{
        val clientPassword = MutableLiveData<String>()
        api.setPassword(password).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response.body().let {
                        clientPassword.postValue(it)
                        Log.d("onSuccessPassword", it.toString())
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onPasswordFailure", t.message.toString())
            }
        })
        return clientPassword
    }

    fun getProfile():LiveData<Profile>{
        val clientPassword = MutableLiveData<Profile>()
        api.getProfile().enqueue(object :Callback<Profile>{
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful){
                    response.body().let {
                        clientPassword.postValue(it)
                        Log.d("onSuccessPassword", it.toString())
                    }
                }
            }
            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.e("onPasswordFailure", t.message.toString())
            }
        })
        return clientPassword
    }
}