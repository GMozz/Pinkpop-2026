package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.MainActivity

class NotificationReceiver : BroadcastReceiver() {
    private val TAG = "NotificationReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        val artistName = intent.getStringExtra("artistName") ?: "Favoriete artiest"
        val stageName = intent.getStringExtra("stageName") ?: "Podium"
        val startTime = intent.getStringExtra("startTime") ?: "Onbekend"

        Log.d(TAG, "Received notification broadcast for $artistName / $stageName / $startTime")

        val channelId = "pinkpop_lineup_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Channel on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Pinkpop Live Notificaties",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaties wanneer je favoriete bands gaan optreden"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent to launch MainActivity when tapped
        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            artistName.hashCode(),
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Use standard dialog info icon or application icon
        val appIcon = com.example.R.mipmap.ic_launcher_round

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(appIcon)
            .setContentTitle("Band begint bijna!")
            .setContentText("$artistName begint over 30 minuten op het $stageName ($startTime)!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 400, 200, 400))
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(artistName.hashCode(), notification)
    }
}
