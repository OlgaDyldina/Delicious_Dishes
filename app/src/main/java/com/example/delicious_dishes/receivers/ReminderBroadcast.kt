package com.example.delicious_dishes.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.delicious_dishes.entity.Recipe
import com.example.delicious_dishes.view.notifications.NotificationConstants
import com.example.delicious_dishes.view.notifications.NotificationHelper


class ReminderBroadcast : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(NotificationConstants.RECIPE_BUNDLE_KEY)
        val recipe: Recipe = bundle?.get(NotificationConstants.RECIPE_KEY) as Recipe

        NotificationHelper.createNotification(context!!, recipe)
    }
}