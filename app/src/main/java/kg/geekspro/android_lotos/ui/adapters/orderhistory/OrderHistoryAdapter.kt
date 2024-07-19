package kg.geekspro.android_lotos.ui.adapters.orderhistory

import android.os.Bundle
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
import kg.geekspro.android_lotos.ui.fragments.profile.order.Orders
import okhttp3.internal.notify

class OrderHistoryAdapter(val onClick:(id:Int)->Unit) : Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    private val orderList = mutableListOf<Orders.Result>()

    fun getOrderList(order: List<Orders.Result>) {
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
        fun bind(orderHistoryModel: Orders.Result) = with(binding) {
            val date = orderHistoryModel.scheduledData.split("T")[0]
            tvDateOfOrder.text = date
            tvHomeAddress.text = orderHistoryModel.address
            tvTypeOfCleaning.text = orderHistoryModel.categoryTitle
            tvStatus.text = orderHistoryModel.status
             if (orderHistoryModel.status == "в_обработке"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.orange))
            }else if (orderHistoryModel.status == "принято"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.purple))
            }else if (orderHistoryModel.status == "завершено"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.green))
            }else if (orderHistoryModel.status == "отменено"){
                statusCardView.setCardBackgroundColor(itemView.context.resources.getColor(R.color.dark_black))
            }
            itemView.setOnClickListener {
                onClick(orderHistoryModel.id)
            }
        }
    }

}