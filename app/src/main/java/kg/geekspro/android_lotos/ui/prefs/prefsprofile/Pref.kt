package kg.geekspro.android_lotos.ui.prefs.prefsprofile

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class Pref @Inject constructor(private val pref:SharedPreferences) {

    fun isShow():Boolean{
        return pref.getBoolean(SHOWED_KEY, false)
    }

    fun onShowed(){
        pref.edit().putBoolean(SHOWED_KEY, true).apply()
    }
    fun saveImage(imageView: String){
        pref.edit().putString(SHOW_IMAGE, imageView).apply()
    }

    fun getImage(): String? {
        return pref.getString(SHOW_IMAGE, null)
    }

    fun saveSessionId(sessionId: String){
        pref.edit().putString(SESSION_ID, sessionId).apply()
    }

    fun getSessionId(): String? {
        return pref.getString(SESSION_ID, null)
    }

    fun saveRefresh(refreshToken: String){
        pref.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    fun getRefresh(): String? {
        return pref.getString(REFRESH_TOKEN, null)
    }

    fun saveAccessToken(accessToken: String){
        pref.edit().putString(ACCESS_TOKEN, accessToken).apply()
        Log.d("accessToken", accessToken)
    }

    fun getSAccessToken(): String? {
        return pref.getString(ACCESS_TOKEN, null)
    }


    companion object{
        const val PREF_NAME = "preffffffffffffffffffffffff.name"
        const val SHOWED_KEY = "showedddddddddddddddddddddddd.key"
        const val SHOW_IMAGE = "showwwwwwwwwwwwwwwwwwwwwwww.image"
        const val SESSION_ID = "session.id"
        const val REFRESH_TOKEN = "refresh.token"
        const val ACCESS_TOKEN = "access.tokenn"
    }
}