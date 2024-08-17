package kg.geekspro.android_lotos.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kg.geekspro.android_lotos.R

const val channelId = "notification_channel"
const val chanelName = "kg.geekspro.android_lotos"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //receive the notification
        if (remoteMessage.getNotification() != null) {
            generateNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        }
    }

    //generate the notification
    fun generateNotification(title: String, message: String) {

        //create intent because when the user click on notification, the app will open
        val intent = Intent(this, MainActivity::class.java)

        //clear all the activities and put this(MainActivity) at the top priority
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //we use this pending activity only once( it will destroy after used once)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        //We use channel id , channel name (after Oreo update)
        //we create notification using NotificationBuilder
        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                //set icons,autoCancel , OnlyAlertOnce
                .setSmallIcon(R.drawable.ic_notifications)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        //attach the builder with the notification layout(create getRemoteView method)

        //notificationManager( Android allows to put notification into the titleBar of your application)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //check user version must be greater than OreoVersion which is Code O(oh not zero)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //create an notificationChannel( all notifications must be assigned to a channel)
            val notificationChannel =
                NotificationChannel(channelId, chanelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        //get notify
        notificationManager.notify(0, builder.build())
    }

    /*override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.also { data ->
            val title = data["title"]
            val message = data["message"]
            val builder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                NotificationCompat.Builder(this, CHANNEL_ID)
            }
            else {
                NotificationCompat.Builder(this)
            }
            val notification = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setGrap
                .setContentTitle(title)
                .setContentText(message)
                .build()
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(0, notification)
        }
    }
    override fun onNewToken(instanceToken: String) {
        Log.i("SampleFCM", "token: $instanceToken")
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            var channel = nm.getNotificationChannel(CHANNEL_ID)
            if (channel == null) {
                channel = NotificationChannel(
                    CHANNEL_ID,
                    "fcm_callback_notification_channel",
                    NotificationManager.IMPORTANCE_HIGH)
                nm.createNotificationChannel(channel)
            }
        }
    }*/

    /*override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_notification)
            .setDestination(R.id.orderHistoryFragment)
            .createPendingIntent()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // ID канала уведомлений
        val channelId = "notify"

        // Создание канала уведомлений (для Android 8.0 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "fcm_fallback_notification_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel Description"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications) // Замените на ваш значок
            .setContentTitle("Order Update") // Пример заголовка
            .setContentText("Your order has been updated!") // Пример текста
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Автоматическое скрытие уведомления при нажатии
            .build()

        notificationManager.notify(0, notification)

        sendNotification(this,message.notification?.title!!,message.notification?.body!!,
            R.id.orderHistoryFragment
        )
    }*/


    /*override fun onMessageReceived(message: RemoteMessage) {
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
     */

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
