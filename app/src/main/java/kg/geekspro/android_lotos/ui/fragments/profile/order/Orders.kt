package kg.geekspro.android_lotos.ui.fragments.profile.order


import com.google.gson.annotations.SerializedName


data class Orders(
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
        @SerializedName("address")
        val address: String,
        @SerializedName("category_title")
        val categoryTitle: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("scheduled_data")
        val scheduledData: String,
        @SerializedName("status")
        val status: String
    )
}