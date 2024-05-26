package kg.geekspro.android_lotos.models.profile

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class Profile(
    //val photo:MultipartBody.Part,
    val photo:File,
    @SerializedName("first_name")
    val firstName:String?=null,
    @SerializedName("last_name")
    val lastName:String?=null,
    @SerializedName("date_of_birth")
    val dateOfBirth:String?=null,
    val address:String?=null,
)
