package kg.geekspro.android_lotos.presentation.ui.profile.password.create

import com.google.gson.annotations.SerializedName

data class Password(
    val password:String,
    @SerializedName("re_password")
    val re_password:String,
)
