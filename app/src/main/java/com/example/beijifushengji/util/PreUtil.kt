package com.seven.music.util

import android.annotation.SuppressLint
import android.content.Context

 object PreUtil {

    private val appname = "SEVEN_MUSIC"

    @SuppressLint("ApplySharedPref")
     fun write(context: Context, key: String, value: Boolean?) {
        val sharedPreferences = context.getSharedPreferences(
            appname, Context.MODE_PRIVATE
        )
        if (value != null) {
            sharedPreferences.edit().putBoolean(key, value).commit()
        }
    }

     fun readBoolean(context: Context, key: String): Boolean? {
         val sharedPreferences = context.getSharedPreferences(
             appname, Context.MODE_PRIVATE
         )
         return sharedPreferences.getBoolean(key, false)
     }



}