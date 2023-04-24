package com.kosign.multimodulehilt.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kosign.multimodulehilt.BaseActivity
import com.kosign.multimodulehilt.viewmodel.HomeViewModel
import com.kosign.multimodulehilt.R
import com.kosign.multimodulehilt.databinding.ActivityHomeBinding
import com.kosign.multimodulehilt.util.CITY_ID
import com.kosign.multimodulehilt.view.widget.MultiStatus
import com.kosign.multimodulehilt.view.widget.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()

    private var dialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = this.viewModel
        binding.weatherIconUrl = "GG"

        dialog = ProgressDialog(this)

        observerDialog()

    }

    private fun observerDialog() {
        viewModel.multiStatus.observe(this) {
            when (it.ordinal){
                MultiStatus.LOADING.ordinal -> {
                    dialog?.show()
                }
                MultiStatus.ERROR.ordinal , MultiStatus.NORMAL.ordinal ->{
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        intent.extras?.getString(CITY_ID)?.let {
            viewModel.checkCurrentWeather(it)
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_home
    }

    override fun setUpViewModel() {
        viewModel.weatherIconUrlLiveData.observe(this, Observer<String> {
            Log.d(">>", "Weather Icon updated $it")
            binding.weatherIconUrl = it
        })
    }
}