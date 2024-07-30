package kg.geekspro.android_lotos.ui.repositories.repomain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geekspro.android_lotos.ActionsByIdItem
import kg.geekspro.android_lotos.models.mainmodels.ActionsModel
import kg.geekspro.android_lotos.models.mainmodels.MainEntities
import kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications.NotificationsModel
import kg.geekspro.android_lotos.ui.interfaces.maininterfeces.MainApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryMain @Inject constructor(
    private val mainApiService: MainApiService, private val pref: Pref
) {

    fun loadMain(): LiveData<MainEntities> {
        val token = pref.getAccessToken()
        val fcmAnswer = MutableLiveData<MainEntities>()

        mainApiService.getMain("Bearer $token").enqueue(object :
            Callback<MainEntities> {
            override fun onResponse(call: Call<MainEntities>, response: Response<MainEntities>) {
                if (response.isSuccessful) {
                    fcmAnswer.value = response.body()
                    Log.d("MuhlisRepository", "Token sent successfully: ${response.body()}")
                    Log.d("MuhlisRepository", "Response data: ${response.body()?.toString()}")
                } else {
                    Log.e(
                        "MuhlisRepository",
                        "Failed to send token: ${response.errorBody()?.string()}"
                    )
                    Log.e(
                        "MuhlisRepository",
                        "Response error: ${response.code()} ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<MainEntities>, t: Throwable) {
                Log.e("MuhlisRepository", "Error sending token", t)
                Log.e("MuhlisRepository", "Failure message: ${t.message}")
            }
        })

        return fcmAnswer
    }

    fun loadActions(): LiveData<ActionsModel> {
        val actions = MutableLiveData<ActionsModel>()

        mainApiService.getActions().enqueue(object : Callback<ActionsModel> {
            override fun onResponse(call: Call<ActionsModel>, response: Response<ActionsModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        actions.postValue(it)
                    }
                } else {
                    Log.d("Actions", "actions didn't come")
                }
            }

            override fun onFailure(call: Call<ActionsModel>, t: Throwable) {
                Log.e("Actions", "Error sending token", t)
            }
        })

        return actions
    }

    fun loadNotifications(): LiveData<NotificationsModel> {
        val notifications = MutableLiveData<NotificationsModel>()

        mainApiService.getNotifications("Bearer ${pref.getAccessToken()}").enqueue(object : Callback<NotificationsModel> {
            override fun onResponse(call: Call<NotificationsModel>, response: Response<NotificationsModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        notifications.postValue(it)
                        Log.e("Actions", it.toString())
                    }
                } else {
                    Log.d("Actions", "notifications didn't come")
                }
            }

            override fun onFailure(call: Call<NotificationsModel>, t: Throwable) {
                Log.e("Actions", "Error sending token", t)
            }
        })

        return notifications
    }

    fun loadActionsById(id:Int): LiveData<ActionsByIdItem> {
        val actions = MutableLiveData<ActionsByIdItem>()

        mainApiService.getActionsById(id).enqueue(object : Callback<ActionsByIdItem> {
            override fun onResponse(call: Call<ActionsByIdItem>, response: Response<ActionsByIdItem>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        actions.postValue(it)
                    }
                } else {
                    Log.d("Actions", "actions didn't come")
                }
            }

            override fun onFailure(call: Call<ActionsByIdItem>, t: Throwable) {
                Log.e("Actions", "Error sending token", t)
            }
        })

        return actions
    }

}
