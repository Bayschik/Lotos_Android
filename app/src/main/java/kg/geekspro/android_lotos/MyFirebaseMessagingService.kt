package kg.geekspro.android_lotos

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.geekspro.android_lotos.activity.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            // Extract data and Intent for navigation
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("fragmentId", "desiredFragmentId")
            }

            // Create a pending intent
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            // Set up notification with pending intent
            val builder = NotificationCompat.Builder(this, "fcm_fallback_notification_channel")
                .setContentIntent(pendingIntent)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)

            // Show the notification
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())
        }
    }
}
