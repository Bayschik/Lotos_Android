package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentPromotionDetailBinding
import kg.geekspro.android_lotos.models.mainmodels.Notification


class PromotionDetailFragment : Fragment() {

    lateinit var binding: FragmentPromotionDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPromotionDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val notificationData = arguments?.getSerializable("notification") as Notification

        binding.tvDate.text = notificationData.createdAt
        binding.tvTitle.text = notificationData.title
        binding.tvDescription.text = notificationData.desc
    }


}