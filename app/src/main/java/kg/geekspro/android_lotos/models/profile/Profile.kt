package kg.geekspro.android_lotos.models.profile

import com.google.gson.annotations.SerializedName

data class Profile(
    val photo:ByteArray,
    @SerializedName("first_name")
    val firstName:String?=null,
    @SerializedName("last_name")
    val lastName:String?=null,
    @SerializedName("date_of_birth")
    val dateOfBirth:String?=null,
    val address:String?=null,
)
