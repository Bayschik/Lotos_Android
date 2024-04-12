package kg.geekspro.android_lotos.presentation.ui.data

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

    companion object{
        const val PREF_NAME = "prefffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff.name"
        const val SHOWED_KEY = "showeddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.key"
        const val SHOW_IMAGE = "showwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww.image"
    }
}