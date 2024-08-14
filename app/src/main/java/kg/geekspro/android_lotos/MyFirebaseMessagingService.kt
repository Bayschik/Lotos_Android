package kg.geekspro.android_lotos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.geekspro.android_lotos.activity.MainActivity
import kotlin.math.log

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("NOTIFICATION", "onMessageReceived: ${message.notification?.channelId}")


        val bundle = Bundle().apply {
            putString("fragment", "orderHistory")
        }

        val pendingIntent = NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java) // Activity
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.orderHistoryFragment) // Destination Fragment
            .setArguments(bundle)
            .createPendingIntent()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Устанавливаем идентификатор канала
        val channelId = "fcm_fallback_notification_channel"

        // Проверяем версию Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "notify"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Создаем уведомление
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications) // Установите ваш иконку уведомления
            .setContentTitle(message.notification?.title ?: "Order Update")
            .setContentText(message.notification?.body ?: "Check your order history for updates.")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Показать уведомление
        notificationManager.notify(0, notificationBuilder.build())
    }

    /*override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM
        val notificationTitle = remoteMessage.data["title"]
        val notificationBody = remoteMessage.data["body"]

        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notify"
            val channelName = "fcm_fallback_notification_channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

        // Create the notification
        val intent = Intent(this, MainActivity::class.java)
        // Replace with your activity
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(
            this,
            "notify"
        )
            .setSmallIcon(R.drawable.ic_notifications) // Replace with your icon
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())

    }*/


}
