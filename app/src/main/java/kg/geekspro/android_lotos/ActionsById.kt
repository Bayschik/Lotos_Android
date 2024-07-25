package kg.geekspro.android_lotos


import com.google.gson.annotations.SerializedName


data class ActionsByIdItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)
