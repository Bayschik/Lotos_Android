package kg.geekspro.android_lotos.models.orderhistorymodels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonalData(
    @SerializedName("first_name")
    val first_name:String,
    @SerializedName("last_name")
    val last_name:String,
    @SerializedName("date_of_birth")
    val date_of_birth:String,
    @SerializedName("phone")
    val phoneNumber:String,
    val address:String,
):Serializable
