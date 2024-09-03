package kg.geekspro.android_lotos.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.geekspro.android_lotos.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.let {
            val id = it.data["channelId"]

            val intent = Intent(this, OrdersActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            /*val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                // Add the intent, which inflates the back stack.
                addNextIntentWithParentStack(intent)
                // Get the PendingIntent containing the entire back stack.
                getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }*/

            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationBuilder = NotificationCompat.Builder(this, id!!)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(it.notification?.title)
                .setContentText(it.notification?.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                if (ActivityCompat.checkSelfPermission(
                        this@MyFirebaseMessagingService,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@with
                }
                // notificationId is a unique int for each notification that you must define.
                notify(0, notificationBuilder.build())
            }
        }

        Log.d("Notification", "${remoteMessage.messageId}")
        Log.d("Notification", "${remoteMessage.data}")
        Log.d("Notification", "${remoteMessage.notification?.channelId}")
        Log.d("Notification", "${remoteMessage.notification?.body}")
    }
}
