package kg.geekspro.android_lotos.presentation.ui.profile

import com.google.gson.annotations.SerializedName

data class Profile(
    val photo:String,
    @SerializedName("first_name")
    val firstName:String,
    @SerializedName("last_name")
    val lastName:String,
    @SerializedName("date_of_birth")
    val dateOfBirth:String,
    val phone:String,
    val email:String,
    val address:String,
)
