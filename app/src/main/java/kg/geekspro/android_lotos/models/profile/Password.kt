package kg.geekspro.android_lotos.models.profile

import com.google.gson.annotations.SerializedName

data class Password(
    val password:String,
    @SerializedName("re_password")
    val re_password:String,
)
