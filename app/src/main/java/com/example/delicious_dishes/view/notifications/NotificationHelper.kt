package com.example.delicious_dishes.view.notifications

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.delicious_dishes.MainActivity
import com.example.delicious_dishes.R
import com.example.delicious_dishes.entity.ApiConstants
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.receivers.ReminderBroadcast

object NotificationHelper {
    fun createNotification(context: Context, recipe: Recipe) {
        val mIntent = Intent(context, MainActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context!!, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.baseline_drive_file_move_outline_24)
            setContentTitle("Не забудьте пориготовить!")
            setContentText(recipe.query)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        val notificationManager = NotificationManagerCompat.from(context)

        Glide.with(context)
            .asBitmap()
            .load(ApiConstants.IMAGES_URL + "w500" + recipe.image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    notificationManager.notify(recipe.id, builder.build())
                }
            })
        notificationManager.notify(recipe.id, builder.build())
    }
    fun notificationSet(context: Context, recipe: Recipe){
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
            val timeSetListener =
                TimePickerDialog.OnTimeSetListener{ _, hourOfDay, pickerMinute ->
                    val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                        dpdYear,
                        dpdMonth,
                        dayOfMonth,
                        hourOfDay,
                        pickerMinute,
                        0
                    )
                    val dateTimeInMillis = pickedDateTime.timeInMillis
                    createWatchLaterEvent(context, dateTimeInMillis, recipe)
                }
                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()
            },
            currentYear,
            currentMonth,
            currentDay
        ).show()
    }
    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, recipe: Recipe){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(recipe.query, null, context, ReminderBroadcast()::class.java)
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.RECIPE_KEY, recipe)
        intent.putExtra(NotificationConstants.RECIPE_BUNDLE_KEY, bundle)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            dateTimeInMillis,
            pendingIntent
        )
    }
}