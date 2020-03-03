package com.example.beijifushengji.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType
import java.lang.Class as Class


abstract class BaseActivity <T:ViewDataBinding,VM:BaseViewModel>(useDataBinding: Boolean = true) : AppCompatActivity() {

    private lateinit var dataBinding:T

    private val _useBinding = useDataBinding

    lateinit var mViewModel: VM

    private lateinit var mViewModelClass: Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            mViewModelClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
            mViewModel = ViewModelProvider(this).get(mViewModelClass)

        } catch (e: Exception) {

        }
        if (_useBinding) {
            dataBinding =  DataBindingUtil.setContentView(this@BaseActivity, getLayoutId())
            dataBinding.lifecycleOwner =this
        } else setContentView(getLayoutId())

        initView()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        dataBinding.unbind()
        super.onDestroy()
    }

}