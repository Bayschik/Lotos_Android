package kg.geekspro.android_lotos.models.orderhistorymodels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonalData(
    @SerializedName("first_name")
    val firstName:String,
    @SerializedName("last_name")
    val lastName:String,
    @SerializedName("date_of_birth")
    val dateOfBirth:String,
    @SerializedName("phone")
    val phoneNumber:String,
    val address:String,
):Serializable
