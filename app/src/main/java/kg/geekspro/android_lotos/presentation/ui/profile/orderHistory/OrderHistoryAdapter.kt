package kg.geekspro.android_lotos.presentation.ui.profile.orderHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geekspro.android_lotos.databinding.ItemOrderHistoryBinding
import kg.geekspro.android_lotos.presentation.ui.model.OrderHistoryModel

class OrderHistoryAdapter : Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    private val orderList = arrayListOf(
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
        ),
        OrderHistoryModel(
            "21 марта, четверг",
            "Уборка после ремонта",
            "Чуй проспекти 12, блок 3, квартира 33"
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
        }

    }

}