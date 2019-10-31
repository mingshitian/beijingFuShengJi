package com.example.beijifushengji.activity

import android.annotation.SuppressLint
import android.view.Menu
import androidx.annotation.ColorLong
import com.example.beijifushengji.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun beforeInitView() {

    }

    private val musicUrl = "https://music.yiroote.com"

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
//        wv_music.loadUrl(musicUrl)
        main_toolbar.title = "Title"
        main_toolbar.subtitle = "Sub Title"
        setSupportActionBar(main_toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }


    override fun initEvent() {

    }

    override fun setStatusColor(@ColorLong long: Long) {
        super.setStatusColor(0xff242124)
    }

}
