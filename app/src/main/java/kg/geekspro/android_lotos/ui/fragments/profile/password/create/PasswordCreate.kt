package kg.geekspro.android_lotos.ui.fragments.profile.password.create


import com.google.gson.annotations.SerializedName


data class PasswordCreate(
    @SerializedName("access")
    val access: String,
    @SerializedName("refresh")
    val refresh: String
)