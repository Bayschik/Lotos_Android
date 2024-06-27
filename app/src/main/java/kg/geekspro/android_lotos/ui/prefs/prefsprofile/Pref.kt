package kg.geekspro.android_lotos.ui.prefs.prefsprofile

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class Pref @Inject constructor(private val pref: SharedPreferences) {
    fun isShow(): Boolean {
        return pref.getBoolean(SHOWED_KEY, false)
    }

    fun onShowed() {
        pref.edit().putBoolean(SHOWED_KEY, true).apply()
    }

    fun isLogOut(): Boolean {
        return pref.getBoolean(LOG_OUT_KEY, false)
    }

    fun onLogOut() {
        pref.edit().putBoolean(LOG_OUT_KEY, true).apply()
    }

    fun saveGmail(gmail: String) {
        pref.edit().putString(GMAIL_KEY, gmail).apply()
    }

    fun getGmail(): String? {
        return pref.getString(GMAIL_KEY, null)
    }

    fun saveNumber(number: String) {
        pref.edit().putString(NUMBER_KEY, number).apply()
    }

    fun getNumber(): String? {
        return pref.getString(NUMBER_KEY, null)
    }


    fun onLogIn() {
        pref.edit().putBoolean(LOG_OUT_KEY, false).apply()
    }

    fun onRegistration() {
        pref.edit().putBoolean(LOG_OUT_KEY, false).apply()
    }

    fun saveSessionId(sessionId: String) {
        pref.edit().putString(SESSION_ID, sessionId).apply()
    }

    fun getSessionId(): String? {
        return pref.getString(SESSION_ID, null)
    }

    fun saveRefreshToken(refreshToken: String) {
        pref.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    fun getRefresh(): String? {
        return pref.getString(REFRESH_TOKEN, null)
    }

    fun saveAccessToken(accessToken: String) {
        pref.edit().putString(ACCESS_TOKEN, accessToken).apply()
        Log.d("accessToken", accessToken)
    }

    fun getAccessToken(): String? {
        return pref.getString(ACCESS_TOKEN, null)
    }

    fun saveChaneEmailSessionId(sessionId: String) {
        pref.edit().putString(EMAIL_SESSION_ID, sessionId).apply()
    }

    fun getChaneEmailSessionId(): String? {
        return pref.getString(EMAIL_SESSION_ID, null)
    }

    companion object {
        const val PREF_NAME = "pref.name"
        const val SHOWED_KEY = "showed.key"
        const val LOG_OUT_KEY = "log.out.key"
        const val GMAIL_KEY = "gmail.key"
        const val NUMBER_KEY = "number.key"
        const val SESSION_ID = "session.id"
        const val REFRESH_TOKEN = "refresh.token"
        const val ACCESS_TOKEN = "access.token"
        const val EMAIL_SESSION_ID = "email.session.id"
    }
}