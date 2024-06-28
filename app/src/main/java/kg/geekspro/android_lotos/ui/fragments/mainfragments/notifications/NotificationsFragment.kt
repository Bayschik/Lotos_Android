package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentNotificationsBinding
import kg.geekspro.android_lotos.models.mainmodels.Notification


class NotificationsFragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding
    lateinit var adapter: NotificationAdapter
//
//    private val adapter = CartAdapter(
//        ::onClick,
//        ::onLongClick
//    )

    private val notificationList = arrayListOf(
        Notification(isPersonal = true, date = "06.05.24", title = "LotosCleaning", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),
        Notification(isPersonal = false, date = "06.05.24", title = "LotosCleaning65", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),
        Notification(isPersonal = true, date = "06.05.24", title = "LotosCleaning", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),
        Notification(isPersonal = false, date = "06.05.24", title = "LotosCleaning66", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),
        Notification(isPersonal = true, date = "06.05.24", title = "LotosCleaning", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),
        Notification(isPersonal = true, date = "06.05.24", title = "LotosCleaning", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),
        Notification(isPersonal = false, date = "06.05.24", title = "LotosCleaning54", time = "16:55", description = "Спасибо за ваш отзыв! Мы очень ценим ваше мнение и всегда рады вашей поддержке."),

    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotificationAdapter(notificationList, ::onClick)

        binding.rvNotifications.adapter = adapter

    }

    private fun onClick(notification: Notification){
        if (notification.isPersonal){
            findNavController().navigate(R.id.promotionDetailFragment, bundleOf("notification" to notification))
        }else{
            findNavController().navigate(R.id.notificationReviewFragment)
        }

    }


}