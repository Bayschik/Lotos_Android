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
                tvDate.text = it.scheduledData
                tvTime.text = it.timeTitle
                tvStatus.text = it.status
                if (it.status == "В ожидании") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.yellow))
                } else if (it.status == "в_обработке") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.orange))
                } else if (it.status == "принято") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.purple))
                } else if (it.status == "завершено") {
                    btnLeaveReview.visibility = View.VISIBLE
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.green))
                } else if (it.status == "отменено") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.dark_black))
                }
                adapter.order(it.servicesData)
            }
            imgBack.setOnClickListener { findNavController().navigateUp() }
            btnLeaveReview.setOnClickListener { findNavController().navigate(R.id.reviewFragment) }
        }
    }

}