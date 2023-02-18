package com.movieapptask.notification

import android.Manifest
import android.R
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMessagingServ"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        getFirebaseMessage(
            remoteMessage.notification!!.title, remoteMessage.notification!!
                .body
        )
    }

    fun getFirebaseMessage(title: String?, msg: String?) {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "myFirebaseChannel")
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
        val manager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.notify(101, builder.build())
    }

//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        Log.d("MyFirebaseIIDService", "Refreshed token: $token")
//    }
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        if (message.data.isNotEmpty()) {
//            Log.d(TAG, "Data Payload: " + message.data)
//            try {
//
//                val title = message.data["title"]
//                val message = message.data["message"]
//                mNotificationManager.textNotification(title,message)
//            } catch (e: Exception) {
//                Log.d(TAG, "Exception: " + e.message)
//            }
//        }
//    }
}