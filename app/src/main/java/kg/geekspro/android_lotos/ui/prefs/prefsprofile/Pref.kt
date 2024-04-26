package kg.geekspro.android_lotos.ui.prefs.prefsprofile

import android.content.SharedPreferences
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

    companion object{
        const val PREF_NAME = "prefffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff.name"
        const val SHOWED_KEY = "showeddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.key"
        const val SHOW_IMAGE = "showwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.image"
    }
}