package com.kosign.multimodulehilt.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kosign.multimodulehilt.R
import com.kosign.multimodulehilt.data.model.item.Item

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mContext : Context
    private var items: List<Item>? = null
    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
//        Log.d(">>>", "setItems: $items")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        Log.d(">>>", "onCreateViewHolder: $viewType")
        mContext = parent.context
        return if (viewType == 0) {
            HeaderHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
            )
        } else {
            ChildHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.child_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d(">>>", "onBindViewHolder: ${getItemViewType(position)}")
        if (getItemViewType(position) == 0) {
            (holder as HeaderHolder).textView.text = items!![position].dateTime
        } else {
            val childHolder = (holder as ChildHolder)
            if (items!![position].meeting == null){
                childHolder.childContainer.visibility = View.GONE
                childHolder.llNow.visibility = View.INVISIBLE
            }else {
                childHolder.childContainer.visibility = View.VISIBLE
                childHolder.llNow.visibility = View.VISIBLE

                childHolder.room.text = items!![position].meeting?.room
                childHolder.agenda.text = items!![position].meeting?.meetingTitle
                childHolder.duration.text = items!![position].meeting?.start_time + " - " + items!![position].meeting?.end_time
                childHolder.itemContainer.setBackground(
                    createRoundDrawable(
                        items!![position].meeting?.stickColor!!,
                        getValueInDP(mContext, 15).toFloat()
                    )
                )

                childHolder.llNow.bringToFront()
                if (items!![position].meeting?.nowBarShowed == true) {
                    childHolder.llNow.setTranslationY(items!![position].meeting?.movePosition!!.toFloat())
                    childHolder.llNow.visibility = View.VISIBLE
                } else {
                    childHolder.llNow.visibility = View.GONE
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items!![position].itemType == 0) 0 else 1
    }

    override fun getItemCount(): Int {
//        Log.d(">>>", "getItemCount: ${items?.size}")
        return items!!.size
    }

    fun createRoundDrawable(solidColor: String, corner: Float): GradientDrawable? {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = corner
        shape.setColor(Color.parseColor(solidColor))
        return shape
    }

    // value in DP
    fun getValueInDP(context: Context, value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    class ChildHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var itemContainer: ConstraintLayout
        var room: TextView
        var agenda : TextView
        var duration : TextView
        var childContainer: ConstraintLayout

        var llNow : LinearLayout

        init {
            childContainer = itemView.findViewById(R.id.item_container)
            llNow = itemView.findViewById(R.id.fl_now)
            room = itemView.findViewById(R.id.textView)
            agenda = itemView.findViewById(R.id.textView2)
            duration = itemView.findViewById(R.id.textView3)
            itemContainer = itemView.findViewById(R.id.item_container)
        }

    }

    class HeaderHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textView: TextView

        init {
            textView = itemView.findViewById(R.id.textView4)
        }

    }
}