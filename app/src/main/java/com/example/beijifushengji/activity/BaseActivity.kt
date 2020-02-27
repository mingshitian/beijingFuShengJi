package com.example.beijifushengji.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity <T:ViewDataBinding>: AppCompatActivity() {

    private lateinit var dataBinding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0){
            dataBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutId())
            dataBinding.lifecycleOwner = this
        }
        initView();
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        dataBinding.unbind()
        super.onDestroy()
    }

}