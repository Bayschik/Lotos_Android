package kg.geekspro.android_lotos.ui.fragments.safety.safetyPassword

import com.google.gson.annotations.SerializedName

data class ChangePassword(
    @SerializedName("old_password")
    val oldPassword:String,
    @SerializedName("new_password")
    val newPassword:String,
    @SerializedName("re_new_password")
    val reNewPassword:String,
)