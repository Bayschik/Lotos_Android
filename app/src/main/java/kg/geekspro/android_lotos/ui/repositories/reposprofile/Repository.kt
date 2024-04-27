package kg.geekspro.android_lotos.ui.repositories.reposprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.ui.interfaces.profileinterfaces.ApiService
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.http2.Header
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiService) {
    private var sessionId: String = ""
    @Inject
    lateinit var client: OkHttpClient

    fun verifyEmail(emailAddress: Registration): LiveData<String> {
        val email = MutableLiveData<String>()

        api.verifyEmail(emailAddress).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                val headerList: Headers = response.headers()
                for (header in headerList) {
                    if (header.first == "Set-Cookie"){
                        if (header.second == "sessionid"){
                            sessionId = header.second
                        }
                    }
                }

                if (response.isSuccessful) {
                    response.body().let {
                        email.postValue(sessionId)
                        Log.d("onSuccessEmail", it.toString())
                        //Log.d("sessionId", client)
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onEmailFailure", t.toString())
            }

        })
        return email
    }

    fun confirmCode(code: VerificationCode): LiveData<String> {
        val codde = MutableLiveData<String>()

        api.confirmCode(code, sessionId).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    response.body().let {
                        codde.postValue(it)
                        Log.d("onSuccessCode", it.toString())
                    }
                } else {
                    Log.d("onCode", "Что-то пошло не так")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onCodeFailure", t.message.toString())
            }

        })
        return codde
    }


    fun clientCreate(data: PersonalData): LiveData<String> {
        val client = MutableLiveData<String>()
        api.clientCreate(data, sessionId).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
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

    fun setPassword(password: Password): LiveData<String> {
        val clientPassword = MutableLiveData<String>()
        api.setPassword(password, sessionId).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
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

    fun getProfile(): LiveData<Profile> {
        val clientPassword = MutableLiveData<Profile>()
        api.getProfile().enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
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