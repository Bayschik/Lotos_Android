package kg.geekspro.android_lotos.ui.fragments.profile.order


import com.google.gson.annotations.SerializedName


data class Order(
    @SerializedName("address")
    val address: String,
    @SerializedName("category_services")
    val categoryServices: String,
    @SerializedName("category_title")
    val categoryTitle: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("rooms_count")
    val roomsCount: Int,
    @SerializedName("scheduled_data")
    val scheduledData: String,
    @SerializedName("services_data")
    val servicesData: List<ServicesData>,
    @SerializedName("status")
    val status: String,
    @SerializedName("type_of_room")
    val typeOfRoom: String
) {
    data class ServicesData(
        @SerializedName("count")
        val count: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("price")
        val price: Double,
        @SerializedName("title")
        val title: String
    )
}