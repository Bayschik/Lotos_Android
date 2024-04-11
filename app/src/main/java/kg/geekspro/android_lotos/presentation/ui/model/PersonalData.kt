package kg.geekspro.android_lotos.presentation.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PersonalData(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val name:String,
    val surName:String,
    val dateOfBirth:String,
    val phoneNumber:String,
    val email:String,
    val address:String,
    val password:String,
):Serializable
