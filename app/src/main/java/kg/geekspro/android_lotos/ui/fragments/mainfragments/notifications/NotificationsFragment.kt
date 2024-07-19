package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.geekspro.android_lotos.databinding.FragmentNotificationsBinding
import kg.geekspro.android_lotos.models.mainmodels.Notification

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    lateinit var binding: FragmentNotificationsBinding
    lateinit var adapter: NotificationAdapter
    private val viewModel: NotificationViewModel by viewModels()
//
//    private val adapter = CartAdapter(
//        ::onClick,
//        ::onLongClick
//    )

    private val notificationList = arrayListOf<Notification>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadNotifications().observe(viewLifecycleOwner) { notifications ->
            if (notifications.results.isNotEmpty()) {
                binding.rvNotifications.adapter = adapter
                notifications?.results?.let { results ->
                    notificationList.clear()
                    adapter = NotificationAdapter(notificationList, ::onClick)
                    results.forEach { result ->
                        val model = Notification(
                            title = result.title,
                            desc = result.description,
                            createdAt = result.createdAt,
                            createdTime = result.time
                        )
                        notificationList.add(model)
                    }
                }
            }else{
                binding.tvNotifications.visibility = View.VISIBLE
            }
        }

    }

    private fun onClick(notification: Notification) {
        /*if (notification.isPersonal){
            findNavController().navigate(R.id.promotionDetailFragment, bundleOf("notification" to notification))
        }else{
            findNavController().navigate(R.id.notificationReviewFragment)
        }*/

    }


}