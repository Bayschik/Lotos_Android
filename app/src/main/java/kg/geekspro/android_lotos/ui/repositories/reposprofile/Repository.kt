package kg.geekspro.android_lotos.ui.repositories.reposprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.ui.fragments.login.LogIn
import kg.geekspro.android_lotos.ui.fragments.profile.logOut.RefreshToken
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.interfaces.profileinterfaces.ApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiService, private val pref:Pref) {
    private lateinit var sessionId:String

    fun verifyEmail(emailAddress: Registration): LiveData<String> {
        val email = MutableLiveData<String>()

        api.verifyEmail(emailAddress).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {
                    val headerList: Headers = response.headers()
                    for (header in headerList) {
                        if (header.first == "Set-Cookie") {
                            val pattern = "sessionid=([^;]+)".toRegex()
                            val matchResult = pattern.find(header.second)

                            val extractedSessionId = matchResult?.groupValues?.get(1)
                            sessionId = "sessionid=$extractedSessionId"
                            pref.saveSessionId(sessionId)
                            break
                        }
                    }
                    response.body().let {
                        email.postValue(sessionId)
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

    fun confirmCode(code: VerificationCode): LiveData<String> {
        val codde = MutableLiveData<String>()
        val session = pref.getSessionId()

        session?.let {
            api.confirmCode(it, code).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        response.body().let {
                            codde.postValue(it)
                            Log.d("отправка данных", it.toString())
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
        }
        return codde
    }


    fun clientCreate(data: PersonalData): LiveData<String> {
        val client = MutableLiveData<String>()
        val session = pref.getSessionId()
        session?.let {
            api.clientCreate(data, it).enqueue(object : Callback<String> {
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
        }
        return client
    }

    fun setPassword(password: Password): LiveData<PasswordCreate> {
        val clientPassword = MutableLiveData<PasswordCreate>()

        val session = pref.getSessionId()
        session?.let {
            api.setPassword(password, it).enqueue(object : Callback<PasswordCreate> {
                override fun onResponse(call: Call<PasswordCreate>, response: Response<PasswordCreate>) {
                    if (response.isSuccessful) {
                        response.body().let {
                            clientPassword.postValue(it)
                            pref.saveAccessToken(it!!.access)
                            pref.saveRefreshToken(it.refresh)
                            Log.d("onSuccessPassword", it.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<PasswordCreate>, t: Throwable) {
                    Log.e("onPasswordFailure", t.message.toString())
                }
            })
        }
        return clientPassword
    }

    fun logIn(logIn: LogIn): LiveData<PasswordCreate> {
        val logInValue = MutableLiveData<PasswordCreate>()

        val session = pref.getSessionId()
        session?.let {
            api.logIn(logIn).enqueue(object : Callback<PasswordCreate> {
                override fun onResponse(call: Call<PasswordCreate>, response: Response<PasswordCreate>) {
                    if (response.isSuccessful) {
                        response.body().let {
                            logInValue.postValue(it)
                            pref.saveAccessToken(it!!.access)
                            Log.d("onSuccessLogIn", it.toString())
                        }
                    }
                    else{
                        Log.d("logIn", "тчо-то пошло не так")
                    }
                }

                override fun onFailure(call: Call<PasswordCreate>, t: Throwable) {
                    Log.e("onLogInFailure", t.message.toString())
                }
            })
        }
        return logInValue
    }

    fun getProfile(): LiveData<Profile> {
        val clientPassword = MutableLiveData<Profile>()

        val accessToken = pref.getAccessToken()!!
        api.getProfile("Bearer $accessToken").enqueue(object : Callback<Profile> {
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

    fun logOut(): LiveData<Unit> {
        val logOut = MutableLiveData<Unit>()
        val refreshToken = pref.getRefresh()!!

        val token = RefreshToken(
            refreshToken = refreshToken
        )
        api.logOut(token).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    response.body().let {
                        logOut.postValue(it)
                        Log.d("onSuccessLogOut", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("onLogOutFailure", t.message.toString())
            }
        })
        return logOut
    }

    fun putDataProfile(refactorData:Profile): LiveData<Profile> {
        val clientPassword = MutableLiveData<Profile>()

        val accessToken = pref.getAccessToken()!!
        api.putProfile(refactorData,"Bearer $accessToken").enqueue(object : Callback<Profile> {
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