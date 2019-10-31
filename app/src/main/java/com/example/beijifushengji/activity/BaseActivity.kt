package com.example.beijifushengji.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.os.Build
import androidx.annotation.ColorLong


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beforeInitView()

        if (useTransStatusBar()) {
            setStatusColor(0xffffffff)
        }

        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }

        initView()

        initEvent()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun beforeInitView()

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initEvent()

    /**
     * if no need to use transparent StatusBar override this method
     */
    protected open fun useTransStatusBar(): Boolean {
        return true
    }

    protected open fun setStatusColor(@ColorLong long: Long) {
        setTranslucent(long)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setTranslucent(@ColorLong long: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or  SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = long.toInt()
        }
    }

}
