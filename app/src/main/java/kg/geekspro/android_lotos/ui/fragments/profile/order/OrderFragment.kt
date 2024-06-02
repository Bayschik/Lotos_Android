package kg.geekspro.android_lotos.ui.fragments.profile.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentOrderBinding

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private val viewModel: OrderViewModel by viewModels()
    private val adapter = OrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("ORDER_ID")
        binding.apply {
            viewModel.getOrderId(id!!).observe(viewLifecycleOwner) {
                rvCleaningServices.adapter = adapter
                tvCleaningCategory.text = it.categoryTitle
                tvCleaningPrice.text = "${it.price.substringBefore(".")} сом"
                tvHomeAddress.text = it.address

                val date = it.scheduledData.split("T")[0]
                tvDate.text = date

                val time = it.scheduledData.split("T")[1]
                tvTime.text = time
                tvStatus.text = it.status
                if (it.status == "В ожидании") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.yellow))
                } else if (it.status == "Принято в обработку") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.orange))
                } else if (it.status == "В работе") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.purple))
                } else if (it.status == "accepted") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.green))
                } else if (it.status == "Отменен") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.dark_black))
                }
                adapter.order(it.servicesData)
            }
            imgBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

}