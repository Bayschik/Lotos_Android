package kg.geekspro.android_lotos.ui.fragments.profile.order

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            viewModel.getOrderId(id!!).observe(viewLifecycleOwner) {
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
                    if (it.reviewStars != 0){
                        ratingBar.visibility = View.VISIBLE
                        ratingBar.rating = it.reviewStars.toFloat()
                    }
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.green))
                } else if (it.status == "отменено") {
                    statusCardView.setCardBackgroundColor(requireContext().resources.getColor(R.color.dark_black))
                }
                adapter.order(it.servicesData)
            }
            imgBack.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigateUp()
            }
            tvWhatCleaning.setOnClickListener { showLogOut() }
            btnLeaveReview.setOnClickListener { findNavController().navigate(R.id.leaveReviewFragment, bundleOf("id of order" to id)) }
        }
    }


    private fun showLogOut() {
        val dialog = layoutInflater.inflate(R.layout.alert_dialog_services, null)

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(dialog)
        val alertShow = alertDialog.create()

        alertShow.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertShow.show()
    }
}