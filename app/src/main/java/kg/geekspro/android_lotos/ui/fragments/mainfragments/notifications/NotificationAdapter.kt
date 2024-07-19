package kg.geekspro.android_lotos.ui.fragments.mainfragments.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geekspro.android_lotos.databinding.NotificationItemsBinding
import kg.geekspro.android_lotos.models.mainmodels.Notification

class NotificationAdapter(
    private val notifications: List<Notification>,
    private val onClick:(notification: Notification)->Unit,
    ): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: NotificationItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification){
            binding.tvDate.text = notification.createdAt
            binding.tvTime.text = notification.createdTime
            binding.tvTitle.text = notification.title
            binding.tvDescription.text = notification.desc

            itemView.setOnClickListener{
                onClick(notification)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return  NotificationViewHolder(NotificationItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

}

