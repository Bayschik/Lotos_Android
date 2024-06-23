package kg.geekspro.android_lotos.models.mainmodels

import java.io.Serializable

data class Notification (
    val id: Int = 0,
    val isPersonal: Boolean,
    val date: String,
    val time: String,
    val title: String,
    val description: String
): Serializable