package kg.geekspro.android_lotos.ui.fragments.profile.logOut

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("refresh_token")
    val refreshToken:String
)
