package com.kosign.multimodulehilt

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getContentView())
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        setUpViewModel()
    }

    @LayoutRes
    protected abstract fun getContentView(): Int

    protected abstract fun setUpViewModel()

}