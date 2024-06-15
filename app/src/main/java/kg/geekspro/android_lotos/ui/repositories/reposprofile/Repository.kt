package kg.geekspro.android_lotos.ui.repositories.reposprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.ReviewModel
import kg.geekspro.android_lotos.models.orderhistorymodels.PersonalData
import kg.geekspro.android_lotos.models.profile.Password
import kg.geekspro.android_lotos.models.profile.Profile
import kg.geekspro.android_lotos.models.registrationmodel.Registration
import kg.geekspro.android_lotos.models.verifycode.VerificationCode
import kg.geekspro.android_lotos.ui.fragments.login.LogIn
import kg.geekspro.android_lotos.ui.fragments.profile.Token
import kg.geekspro.android_lotos.ui.fragments.profile.TokenVerify
import kg.geekspro.android_lotos.ui.fragments.profile.logOut.RefreshToken
import kg.geekspro.android_lotos.ui.fragments.profile.order.Order
import kg.geekspro.android_lotos.ui.fragments.profile.order.OrderList
import kg.geekspro.android_lotos.ui.fragments.profile.password.create.PasswordCreate
import kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail.ChangeEmail
import kg.geekspro.android_lotos.ui.fragments.safety.safetyEmail.Code
import kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword.ChangePassword
import kg.geekspro.android_lotos.ui.interfaces.profileinterfaces.ApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiService, private val pref: Pref) {
    private lateinit var sessionId: String

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
                } else {
                    email.postValue("Аккаунт уже зарегистрирован")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("onEmailFailure", t.toString())
            }
        })
        return email
    }

    fun googleAuth(): LiveData<Unit> {
        val auth = MutableLiveData<Unit>()

        api.googleAuth().enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("auth", "auth is ok $response")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("auth", "auth is not ok")
            }

        })
        return auth
    }

    fun confirmCode(code: VerificationCode): LiveData<String> {
        val codeResult = MutableLiveData<String>()
        val session = pref.getSessionId()

        session?.let {
            api.confirmCode(it, code).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        response.body()?.let { result ->
                            codeResult.postValue(result)
                            Log.d("onSuccessCode", result)
                        }
                    } else {
                        codeResult.postValue("Неверный код")
                        Log.d("onCode", "неверный код либо же что-то пошло не так")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    codeResult.postValue(t.message)
                    Log.e("onCodeFailure", t.message.toString())
                }

            })
        }
        return codeResult
    }


    fun clientCreate(data: PersonalData): LiveData<String> {
        val client = MutableLiveData<String>()
        val session = pref.getSessionId()
        session?.let {
            api.clientCreate(data, it).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        response.body().let { result ->
                            client.postValue(result!!)
                            Log.d("onSuccessCreate", result)
                        }
                    }else{
                        client.postValue(it)
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
                override fun onResponse(
                    call: Call<PasswordCreate>,
                    response: Response<PasswordCreate>
                ) {
                    if (response.isSuccessful) {
                        response.body().let { result ->
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

    fun logIn(logIn: LogIn): LiveData<PasswordCreate> {
        val logInValue = MutableLiveData<PasswordCreate>()

        api.logIn(logIn).enqueue(object : Callback<PasswordCreate> {
            override fun onResponse(
                call: Call<PasswordCreate>,
                response: Response<PasswordCreate>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        pref.saveRefreshToken(result.refresh)
                        pref.saveAccessToken(result.access)
                        logInValue.postValue(result)
                        Log.d("onSuccessLogIn", result.toString())
                    }
                } else {
                    Log.d("logIn", "что-то пошло не так")
                }
            }

            override fun onFailure(call: Call<PasswordCreate>, t: Throwable) {
                Log.e("onLogInFailure", t.message.toString())
            }
        })
        return logInValue
    }

    fun getProfile(accessToken: String? = null): LiveData<Profile> {
        val profile = MutableLiveData<Profile>()
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

    fun getOrderList(): LiveData<List<OrderList.OrderListItem>> {
        val order = MutableLiveData<List<OrderList.OrderListItem>>()
        api.getOrderList("Bearer ${pref.getAccessToken()}").enqueue(object : Callback<List<OrderList.OrderListItem>> {
            override fun onResponse(call: Call<List<OrderList.OrderListItem>>, response: Response<List<OrderList.OrderListItem>>) {
                if (response.isSuccessful) {
                    response.body().let {
                        order.postValue(it)
                        Log.d("onSuccessOrder", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<OrderList.OrderListItem>>, t: Throwable) {
                Log.e("onOrderFailure", t.message.toString())
            }
        })
        return order
    }

    fun getOrderId(id:Int): LiveData<Order> {
        val order = MutableLiveData<Order>()
        api.getOrderId(id,"Bearer ${pref.getAccessToken()}").enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    response.body().let {
                        order.postValue(it)
                        Log.d("onSuccessOrder", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.e("onOrderFailure", t.message.toString())
            }
        })
        return order
    }

    fun logOut(): LiveData<Unit> {
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

    fun putDataProfile(
        image: MultipartBody.Part,
        firstName: RequestBody,
        lastName: RequestBody,
        dateOfBirth: RequestBody,
        address: RequestBody
    ): LiveData<Profile> {
        val putData = MutableLiveData<Profile>()
        val accessToken = pref.getAccessToken()!!
        api.putProfile(image, firstName, lastName, dateOfBirth, address, "Bearer $accessToken")
            .enqueue(object : Callback<Profile> {
                override fun onResponse(
                    call: Call<Profile>,
                    response: Response<Profile>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            putData.postValue(it)
                            Log.d("putDataProfile", "Data sent successfully: $it")
                        } ?: run {
                            Log.e("putDataProfile", "Response body is null")
                        }
                    } else {
                        Log.e("putDataProfile", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    Log.e("putDataProfile", "Failed to send profile data: ${t.message}")
                }
            })
        return putData
    }


    fun changeEmail(changeEmail: ChangeEmail): LiveData<Any> {
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

    fun changeEmailConfirm(code: Code): LiveData<String> {
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

    fun changePassword(changePassword: ChangePassword): LiveData<Unit> {
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

    fun checkUser(accessToken: Token): LiveData<TokenVerify> {
        val user = MutableLiveData<TokenVerify>()

        api.checkUser(accessToken).enqueue(object : Callback<TokenVerify> {
            override fun onResponse(call: Call<TokenVerify>, response: Response<TokenVerify>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        user.postValue(it)
                        Log.d("onSuccessCheckUser", it.toString())
                    }
                } else {
                    val verifyToken = TokenVerify(
                        detail = "Token is invalid or expired",
                        code = "token_not_valid"
                    )
                    user.postValue(verifyToken)
                    Log.d("onTokenFail", "ololo")
                }
            }

            override fun onFailure(call: Call<TokenVerify>, t: Throwable) {
                Log.e("onCheckUserFailure", t.message.toString())
            }
        })
        return user
    }

    fun refreshToken(refreshToken: kg.geekspro.android_lotos.ui.fragments.profile.RefreshToken): LiveData<PasswordCreate> {
        val refresh = MutableLiveData<PasswordCreate>()

        api.refreshToken(refreshToken).enqueue(object : Callback<PasswordCreate> {
            override fun onResponse(
                call: Call<PasswordCreate>,
                response: Response<PasswordCreate>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        pref.saveAccessToken(it.access)
                        pref.saveRefreshToken(it.refresh)
                        refresh.postValue(it)
                        Log.d("onSuccessRefresh", it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<PasswordCreate>, t: Throwable) {
                Log.e("onRefreshFailure", t.message.toString())
            }
        })
        return refresh
    }

    fun leaveReview(reviewModel: ReviewModel): LiveData<ReviewModel> {
        val refresh = MutableLiveData<ReviewModel>()

        api.leaveReview(reviewModel, pref.getAccessToken()!!).enqueue(object : Callback<ReviewModel> {
            override fun onResponse(
                call: Call<ReviewModel>,
                response: Response<ReviewModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {

                    }
                }
            }

            override fun onFailure(call: Call<ReviewModel>, t: Throwable) {
                Log.e("", t.message.toString())
            }
        })
        return refresh
    }
}