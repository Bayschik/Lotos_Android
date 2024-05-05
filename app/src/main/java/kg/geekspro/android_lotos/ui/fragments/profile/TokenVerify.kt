package kg.geekspro.android_lotos.ui.fragments.profile

import com.google.gson.annotations.SerializedName

data class TokenVerify(
    @SerializedName("code")
    val code: String,
    @SerializedName("detail")
    val detail: String
)