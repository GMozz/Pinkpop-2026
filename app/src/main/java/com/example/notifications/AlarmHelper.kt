package com.example.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.model.FestivalDay
import com.example.model.Performance
import java.util.Calendar

object AlarmHelper {
    private const val TAG = "AlarmHelper"

    fun scheduleAlarmForPerformance(context: Context, day: FestivalDay, performance: Performance, stageName: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        
        val performanceTimeMs = calculateTriggerTimeMs(day.dateString, performance.startTime)
        val triggerTimeMs = performanceTimeMs - 30 * 60 * 1000 // 30 minutes in advance
        
        // If the calculated warning trigger time is in the past compared to current system time, don't schedule it.
        if (triggerTimeMs < System.currentTimeMillis()) {
            Log.d(TAG, "Not scheduling alarm for ${performance.artist} because the 30-minute warning is in the past.")
            return
        }

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("artistName", performance.artist)
            putExtra("stageName", stageName)
            putExtra("startTime", performance.startTime)
        }

        val requestCode = performance.artist.hashCode()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Exact alarm scheduling
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTimeMs, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTimeMs, pendingIntent)
            }
            Log.d(TAG, "Successfully scheduled alarm for ${performance.artist} at $triggerTimeMs (time string: ${performance.startTime})")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule alarm", e)
        }
    }

    fun cancelAlarmForPerformance(context: Context, performance: Performance) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode = performance.artist.hashCode()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            Log.d(TAG, "Cancelled alarm for ${performance.artist}")
        }
    }

    private fun calculateTriggerTimeMs(dateString: String, timeString: String): Long {
        // Parse "dd-MM-yyyy"
        val dateParts = dateString.split("-")
        val day = dateParts[0].toInt()
        val month = dateParts[1].toInt() - 1 // Calendar months are 0-based
        val year = dateParts[2].toInt()

        // Parse "HH:mm"
        val timeParts = timeString.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Handle early morning late night shows: if hour is 0, 1, 2, 3, 4, 5
        // they belong to the next morning calendar date
        if (hour in 0..5) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return calendar.timeInMillis
    }
}
