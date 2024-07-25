package kg.geekspro.android_lotos

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.geekspro.android_lotos.activity.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Отправьте новый токен на ваш сервер
        Log.d("FCM", "New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM", "Message received from: ${message.from}")

        message.notification?.let {
            val title = it.title ?: "Default Title"
            val body = it.body ?: "Default Body"

            showNotification(title, body, message.data)
        }
    }

    private fun showNotification(title: String, body: String, data: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("targetFragment", data["targetFragment"])
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "your_channel_id")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                applicationContext as Activity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1
            )
            return
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    /*
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationData = remoteMessage.data
        val title = notificationData["title"] ?: ""
        val body = notificationData["body"] ?: ""
        val fragmentName = notificationData["fragment_name"] ?: ""

        // Create a PendingIntent to open the app and navigate to the fragment
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra("fragment_name", fragmentName)
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        // Create and send the notification
        // ... (notification creation code)
    }
     */

    private fun getFirebaseMessage(title: String, body: String, pendingIntent: PendingIntent) {
        val builder = NotificationCompat.Builder(this, "notify")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val managerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        managerCompat.notify(102, builder.build())
    }
}
