package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications


import com.google.gson.annotations.SerializedName


data class NotificationsModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("action_id")
        val actionId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("order_id")
        val orderId: Any,
        @SerializedName("time")
        val time: String,
        @SerializedName("title")
        val title: String
    )
}