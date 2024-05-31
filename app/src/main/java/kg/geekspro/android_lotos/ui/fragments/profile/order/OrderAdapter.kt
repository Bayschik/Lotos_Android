package kg.geekspro.android_lotos.ui.fragments.profile.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geekspro.android_lotos.databinding.ItemAdditionalServicesBinding

class OrderAdapter : Adapter<OrderAdapter.OrderViewHolder>() {
    private val orderList = arrayListOf<Order.ServicesData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemAdditionalServicesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = orderList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    fun order(servicesData: List<Order.ServicesData>) {
        orderList.clear()
        orderList.addAll(servicesData)
        notifyDataSetChanged()
    }

    inner class OrderViewHolder(private val binding: ItemAdditionalServicesBinding) :
        ViewHolder(binding.root) {
        fun bind(order: Order.ServicesData) {
            binding.apply {
                tvServiceName.text = order.title
                tvServiceCount.text = order.count.toString()
                tvServicePrice.text = "${order.price.toInt()} сом"
            }
        }

    }
}