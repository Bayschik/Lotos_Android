package kg.geekspro.android_lotos.models.mainmodels

import java.io.Serializable

data class CleaningService(
    val id: Int ,
    val name: String,
    var amount: Int = 0,
    val price: Int
) : Serializable