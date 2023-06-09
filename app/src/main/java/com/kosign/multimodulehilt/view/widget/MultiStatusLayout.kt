package com.kosign.multimodulehilt.view.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.kosign.multimodulehilt.R
import com.kosign.multimodulehilt.viewmodel.HomeViewModel

class MultiStatusLayout : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initializeView(context, attrs)
    }

    private fun initializeView(
        context: Context?,
        attrs: AttributeSet?,
        layout: Int = R.layout.layout_multi_status
    ) {
        View.inflate(context, layout, this)
//        val inflater = LayoutInflater.from(context)
//        inflater.inflate(layout, this)
//        binding = LayoutMultiStatusBinding.inflate(inflater)
    }

    fun setViewModel(viewModel: HomeViewModel) {
        //binding.viewModel = viewModel
    }
}

enum class MultiStatus {
    NORMAL,
    LOADING,
    ERROR
}
