package kg.geekspro.android_lotos.ui.prefs.prefsprofile

import android.content.Context

class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

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

    fun saveRefreshToken(refreshToken: String){
        pref.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    fun getRefresh(): String? {
        return pref.getString(REFRESH_TOKEN, null)
    }

    fun saveAccessToken(accessToken: String){
        pref.edit().putString(ACCESS_TOKEN, accessToken).apply()
        Log.d("accessToken", accessToken)
    }

    fun getAccessToken(): String? {
        return pref.getString(ACCESS_TOKEN, null)
    }

    fun saveChaneEmailSessionId(sessionId: String){
        pref.edit().putString(EMAIL_SESSION_ID, sessionId).apply()
    }

    fun getChaneEmailSessionId(): String? {
        return pref.getString(EMAIL_SESSION_ID, null)
    }

    companion object{
        const val PREF_NAME = "prefffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff.name"
        const val SHOWED_KEY = "showeddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.key"
        const val SHOW_IMAGE = "showwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.image"
    }
}