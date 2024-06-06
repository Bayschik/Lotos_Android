package kg.geekspro.android_lotos.ui.adapters.orderhistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ItemOrderHistoryBinding
import kg.geekspro.android_lotos.ui.fragments.profile.order.OrderList
import okhttp3.internal.notify

class OrderHistoryAdapter(val onClick:(id:Int)->Unit) : Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    private val orderList = mutableListOf<OrderList.OrderListItem>()

    fun getOrderList(order: List<OrderList.OrderListItem>) {
        orderList.clear()
        orderList.addAll(order)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        return OrderHistoryViewHolder(
            ItemOrderHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = orderList.size

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    inner class OrderHistoryViewHolder(private val binding: ItemOrderHistoryBinding) :
        ViewHolder(binding.root) {
        fun bind(orderHistoryModel: OrderList.OrderListItem) = with(binding) {
            val date = orderHistoryModel.scheduledData.split("T")[0]
            tvDateOfOrder.text = date
            tvHomeAddress.text = orderHistoryModel.address
            tvTypeOfCleaning.text = orderHistoryModel.categoryTitle
            tvStatus.text = orderHistoryModel.status
            if (orderHistoryModel.status == "В ожидании"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.yellow))
            }else if (orderHistoryModel.status == "Принято в обработку"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.orange))
            }else if (orderHistoryModel.status == "В работе"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.purple))
            }else if (orderHistoryModel.status == "accepted"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.green))
            }else if (orderHistoryModel.status == "Отменен"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.dark_black))
            }
            itemView.setOnClickListener {
                onClick(orderHistoryModel.id)
            }
        }
    }

}