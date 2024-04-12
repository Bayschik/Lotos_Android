package kg.geekspro.android_lotos.presentation.ui.fillData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class PersonalData(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    @SerializedName("first_name")
    val name:String,
    @SerializedName("last_name")
    val surName:String,
    @SerializedName("date_of_birth")
    val dateOfBirth:String,
    @SerializedName("phone")
    val phoneNumber:String,
    val email:String,
    val address:String,
):Serializable
