package com.example

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.tencent.smtt.sdk.QbSdk

class MyApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    override fun onCreate() {
        super.onCreate()
        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {

            }
        }
        QbSdk.initX5Environment(this, cb)
    }


}
