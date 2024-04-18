package kg.geekspro.android_lotos.adapters.orderHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.ItemOrderHistoryBinding
import kg.geekspro.android_lotos.models.orderHistoryModel.OrderHistoryModel

class OrderHistoryAdapter : Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    private val orderList = arrayListOf(
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "В ожидании"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Принято в обработку"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "В работе"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Завершено"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Отменен"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Завершено"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Принято в обработку"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Завершено"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "В ожидании"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Отменен"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33",
            "Завершено"
        ),
    )

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
        fun bind(orderHistoryModel: OrderHistoryModel) = with(binding) {
            tvDateOfOrder.text = orderHistoryModel.date
            tvHomeAddress.text = orderHistoryModel.homeAddress
            tvTypeOfCleaning.text = orderHistoryModel.typeOfCleaning
            tvStatus.text = orderHistoryModel.status
            statusCardView.radius = 12f
            if (orderHistoryModel.status == "В ожидании"){
                statusCardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.yellow))
            }else if (orderHistoryModel.status == "Принято в обработку"){
                statusCardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.orange))
            }else if (orderHistoryModel.status == "В работе"){
                statusCardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.purple))
            }else if (orderHistoryModel.status == "Завершено"){
                statusCardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green))
            }else if (orderHistoryModel.status == "Отменен"){
                statusCardView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_black))
            }
        }

    }

}