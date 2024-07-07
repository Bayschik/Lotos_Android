package kg.geekspro.android_lotos.ui.fragments.profile.order

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentOrderBinding
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private val viewModel: OrderViewModel by viewModels()
    private val adapter = OrderAdapter()
    private val orderAdapter = WhatServicesAdapter()
    @Inject
    lateinit var pref: Pref

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
            rvCleaningServices.adapter = adapter
            viewModel.getOrderId(id!!).observe(viewLifecycleOwner) {order->
                tvCleaningCategory.text = order.categoryTitle
                tvCleaningPrice.text = "${order.price.substringBefore(".")} сом"
                tvHomeAddress.text = order.address
                tvDate.text = order.scheduledData
                tvTime.text = order.timeTitle
                tvStatus.text = order.status
                if (order.status == "В ожидании") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.yellow))
                } else if (order.status == "в_обработке") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.orange))
                } else if (order.status == "принято") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.purple))
                } else if (order.status == "завершено") {
                    btnLeaveReview.visibility = View.VISIBLE
                    if (order.reviewStars != 0){
                        ratingBar.visibility = View.VISIBLE
                        btnLeaveReview.visibility = View.GONE
                        ratingBar.rating = order.reviewStars.toFloat()
                    }
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.green))
                } else if (order.status == "отменено") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.dark_black))
                }
                adapter.order(order.servicesData)
                tvWhatCleaning.setOnClickListener { showServices(order.categoryServices) }
            }
            imgBack.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(R.id.orderHistoryFragment)
            }
            btnLeaveReview.setOnClickListener { findNavController().navigate(R.id.leaveReviewFragment, bundleOf("id of order" to id)) }
        }
    }


    private fun showServices(order: String) {
        val dialog = layoutInflater.inflate(R.layout.alert_dialog_services, null)
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.rv_what_services)

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(dialog)
        val alertShow = alertDialog.create()

        recyclerView.adapter = orderAdapter
        orderAdapter.order(order)

        alertShow.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertShow.show()
    }
}