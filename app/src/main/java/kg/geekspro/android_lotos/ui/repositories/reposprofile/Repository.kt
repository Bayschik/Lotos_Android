package kg.geekspro.android_lotos.ui.repositories.reposprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.ui.fragments.login.LogIn
import kg.geekspro.android_lotos.ui.fragments.profile.Token
import kg.geekspro.android_lotos.ui.fragments.profile.TokenVerify
import kg.geekspro.android_lotos.ui.fragments.profile.logOut.RefreshToken
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail.ChangeEmail
import kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail.Code
import kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword.ChangePassword
import kg.geekspro.android_lotos.ui.interfaces.profileinterfaces.ApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import kotlinx.coroutines.Dispatchers
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiService, private val pref: Pref) {
    private lateinit var sessionId: String

    suspend fun verifyEmail(emailAddress: Registration): LiveData<String> = liveData(Dispatchers.IO){
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
        emit(email.toString())
    }

    suspend fun confirmCode(code: VerificationCode): LiveData<String> {
        val codde = MutableLiveData<String>()
        val session = pref.getSessionId()

        session?.let {
            api.confirmCode(it, code).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        response.body().let {result->
                            codde.postValue(result!!)
                            Log.d("отправка данных", result)
                            Log.d("onSuccessCode", result)
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


    suspend fun clientCreate(data: PersonalData): LiveData<String> {
        val client = MutableLiveData<String>()
        val session = pref.getSessionId()
        session?.let {
            api.clientCreate(data, it).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        response.body().let {result->
                            client.postValue(result!!)
                            Log.d("onSuccessCreate", result)
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

    suspend fun setPassword(password: Password): LiveData<PasswordCreate> {
        val clientPassword = MutableLiveData<PasswordCreate>()

        val session = pref.getSessionId()
        session?.let {
            api.setPassword(password, it).enqueue(object : Callback<PasswordCreate> {
                override fun onResponse(
                    call: Call<PasswordCreate>,
                    response: Response<PasswordCreate>
                ) {
                    if (response.isSuccessful) {
                        response.body().let {result->
                            clientPassword.postValue(result!!)
                            pref.saveAccessToken(result.access)
                            pref.saveRefreshToken(result.refresh)
                            Log.d("onSuccessPassword", result.toString())
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

    suspend fun logIn(logIn: LogIn): LiveData<PasswordCreate> {
        val logInValue = MutableLiveData<PasswordCreate>()

        val session = pref.getSessionId()
        session?.let {
            api.logIn(logIn).enqueue(object : Callback<PasswordCreate> {
                override fun onResponse(
                    call: Call<PasswordCreate>,
                    response: Response<PasswordCreate>
                ) {
                    if (response.isSuccessful) {
                        response.body().let {result->
                            logInValue.postValue(result!!)
                            pref.saveAccessToken(result.access)
                            Log.d("onSuccessLogIn", result.toString())
                        }
                    } else {
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

    suspend fun getProfile(): LiveData<Profile> {
        val profile = MutableLiveData<Profile>()

        val accessToken = pref.getAccessToken()!!
        api.getProfile("Bearer $accessToken").enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    response.body().let {
                        profile.postValue(it)
                        Log.d("onSuccessPassword", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.e("onPasswordFailure", t.message.toString())
            }
        })
        return profile
    }

    suspend fun logOut(): LiveData<Unit> {
        val logOut = MutableLiveData<Unit>()
        val refreshToken = pref.getRefresh()!!

        val token = RefreshToken(
            refreshToken = refreshToken
        )
        api.logOut("Bearer ${pref.getAccessToken()}", token).enqueue(object : Callback<Unit> {
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

    suspend fun putDataProfile(refactorData: Profile): LiveData<Profile> {
        val putData = MutableLiveData<Profile>()

        val accessToken = pref.getAccessToken()!!
        api.putProfile(refactorData, "Bearer $accessToken").enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    response.body().let {
                        putData.postValue(it)
                        Log.d("onSuccessPassword", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.e("onPasswordFailure", t.message.toString())
            }
        })
        return putData
    }

    suspend fun changeEmail(changeEmail: ChangeEmail): LiveData<Any> {
        val emailChange = MutableLiveData<Any>()

        val accessToken = pref.getAccessToken()!!
        api.changeEmail("Bearer $accessToken", changeEmail).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val headerList: Headers = response.headers()
                    for (header in headerList) {
                        if (header.first == "Set-Cookie") {
                            val pattern = "sessionid=([^;]+)".toRegex()
                            val matchResult = pattern.find(header.second)

                            val extractedSessionId = matchResult?.groupValues?.get(1)
                            sessionId = "sessionid=$extractedSessionId"
                            pref.saveChaneEmailSessionId(sessionId)
                            break
                        }
                    }
                    response.body().let {
                        emailChange.postValue(sessionId)
                        Log.d("onSuccessChangeEmail", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("onChangeEmailFailure", t.message.toString())
            }
        })
        return emailChange
    }

    suspend fun changeEmailConfirm(code: Code): LiveData<String> {
        val confirmEmail = MutableLiveData<String>()

        val accessToken = pref.getAccessToken()!!
        val session = pref.getChaneEmailSessionId()
        session?.let { id ->
            api.changeEmailConfirm("Bearer $accessToken", id, code)
                .enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            response.body().let {
                                confirmEmail.postValue(it)
                                Log.d("onSuccessChangeEmailCode", it.toString())
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("onChangeEmailCodeFailure", t.message.toString())
                    }
                })
        }
        return confirmEmail
    }

    suspend fun changePassword(changePassword: ChangePassword): LiveData<Unit> {
        val password = MutableLiveData<Unit>()

        val accessToken = pref.getAccessToken()!!
        api.changePassword("Bearer $accessToken", changePassword).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    response.body().let {
                        password.postValue(it)
                        Log.d("onSuccessChangeEmailCode", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("onChangeEmailCodeFailure", t.message.toString())
            }
        })
        return password
    }

    suspend fun checkUser(accessToken: Token): LiveData<TokenVerify> {
        val user = MutableLiveData<TokenVerify>()

        api.checkUser(accessToken).enqueue(object : Callback<TokenVerify> {
            override fun onResponse(call: Call<TokenVerify>, response: Response<TokenVerify>) {
                if (response.isSuccessful) {
                    response.body().let {
                        user.postValue(it)
                        Log.d("onSuccessCheckUser", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<TokenVerify>, t: Throwable) {
                Log.e("onCheckUserFailure", t.message.toString())
            }
        })
        return user
    }

    // client_create = Set-Cookie: sessionid=62pb53bd0jusvjmzufpcmvo25ddkhpb6
    // client_create/confirm/, code=8548
    // client_create/set_password/, "password":"123456789","re_password":"123456789"
}