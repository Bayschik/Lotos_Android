package kg.geekspro.android_lotos.ui.fragments.profile.order


import com.google.gson.annotations.SerializedName


class OrderList : ArrayList<OrderList.OrderListItem>(){
    data class OrderListItem(
        val id: Int,
        @SerializedName("scheduled_data")
        val scheduledData: String,
        val status: String,
        @SerializedName("category_title")
        val categoryTitle: String,
        val address: String
    )

}