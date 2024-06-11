package kg.geekspro.android_lotos


import android.net.Uri
import com.google.gson.annotations.SerializedName


data class ReviewModel(
    @SerializedName("images")
    val images: List<Uri>,
    @SerializedName("order_id")
    val orderId: Int,
    @SerializedName("stars")
    val stars: Int,
    @SerializedName("text")
    val text: String
)