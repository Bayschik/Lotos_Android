package kg.geekspro.android_lotos.models.profile

import com.google.gson.annotations.SerializedName

data class Profile(
    val photo:String,
    @SerializedName("first_name")
    val firstName:String,
    @SerializedName("last_name")
    val lastName:String,
    @SerializedName("date_of_birth")
    val dateOfBirth:String,
    val address:String,
)
