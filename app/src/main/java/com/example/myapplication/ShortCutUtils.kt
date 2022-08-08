package com.example.myapplication

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast

class ShortCutUtils {

    companion object {
        private var isRegisterReceiver: Boolean = false
        private val callBackReceiver = CallBackReceiver()

        fun createShortCut(
            context: Context,
            targetClass: Class<out Activity>,
            shortCutId: String,
            label: String,
            iconResId: Int
        ) {
            val shortcutManager: ShortcutManager =
                (context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager)

            val shortcutInfoIntent = Intent(context, targetClass).setAction(Intent.ACTION_VIEW)

            val shortcutInfo = ShortcutInfo.Builder(context, shortCutId)
                .setIcon(Icon.createWithResource(context, iconResId))
                .setShortLabel(label)
                .setIntent(shortcutInfoIntent)
                .build();

            if (!isRegisterReceiver) {
                registerReceiver(context)
                isRegisterReceiver = true
                Log.e("1", "register")
            } else {
                Log.e("1", "has registered")
            }

            //当添加快捷方式的确认弹框弹出来时，将被回调CallBackReceiver里面的onReceive方法
            val shortcutCallbackIntent = PendingIntent.getBroadcast(
                context, 0, Intent(
                    context,
                    CallBackReceiver::class.java
                ), PendingIntent.FLAG_IMMUTABLE
            )
            shortcutManager.requestPinShortcut(
                shortcutInfo,
                shortcutCallbackIntent?.getIntentSender()
            )
        }

        private fun registerReceiver(context: Context) {
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.intent.action.CREATE_SHORTCUT")
            context.registerReceiver(callBackReceiver, intentFilter)
        }
    }


    class CallBackReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "成功创建快捷方式", Toast.LENGTH_SHORT).show()
            Log.e("1", "create shortcut")
        }
    }


}