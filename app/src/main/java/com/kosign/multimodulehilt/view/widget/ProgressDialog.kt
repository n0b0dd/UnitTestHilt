package com.kosign.multimodulehilt.view.widget

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.kosign.multimodulehilt.R

class ProgressDialog(context: Context) : Dialog(context) {

    init {
        val view = ProgressBar(context )
        Log.d(">>>", "Loading: ${view.isShown}")
        view.progressDrawable = ContextCompat.getDrawable(context, R.drawable.circular_loading)
        this.setContentView(view)
        this.setCancelable(false)
        show()
    }

}