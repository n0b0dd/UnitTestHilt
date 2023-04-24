package com.kosign.multimodulehilt.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosign.multimodulehilt.BaseActivity
import com.kosign.multimodulehilt.R
import com.kosign.multimodulehilt.data.model.Position
import com.kosign.multimodulehilt.data.model.Reservation
import com.kosign.multimodulehilt.data.model.item.Data
import com.kosign.multimodulehilt.data.model.item.Item
import com.kosign.multimodulehilt.data.model.item.Meeting
import com.kosign.multimodulehilt.databinding.ActivityMainBinding
import com.kosign.multimodulehilt.util.CITY_ID
import com.kosign.multimodulehilt.util.CITY_NAME_PREFIX
import com.kosign.multimodulehilt.view.widget.DividerItemDecorator
import com.kosign.multimodulehilt.view.widget.MultiStatus
import com.kosign.multimodulehilt.view.widget.ProgressDialog
import com.kosign.multimodulehilt.viewmodel.MainViewModel
import com.mobile.bnkcl.util.blurview.SupportRenderScriptBlur
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel : MainViewModel by viewModels()
    var items :ArrayList<Item>? = null
    val positions = ArrayList<Position>()
    var smoothScrollToPosition = 0
    var countdownSecond = 0
    var position = 0
    var showNowBarOnItem = 0
    var maxChildHeight = 0
    var currentDateTimeString = ""
    private lateinit var adapter :HomeScreenAdapter

//    private lateinit var adapter :RecyclerViewAdapter

    private var dialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(">>", "onCreate: ")
        binding.viewmodel = viewModel

//        viewModel.getTextLabel()
//        binding.container.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            intent.putExtra("key", viewModel.label.value)
//            homeResult.launch(intent)
//        }

        initUI()
        observerDialog()
    }

    private fun clock() {
        getTime()
        val hander = Handler()
        Thread {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            hander.post {
                getTime()
                clock()
            }
        }.start()
    }

    fun getTime() {
        val d = Date()
        val sdf = SimpleDateFormat("hh:mm:ss a")
        currentDateTimeString = sdf.format(d)
        Log.d(">>>", "getChildView: $currentDateTimeString")

//        listData.get(0).getMeetings().get(0).setNowBarShowed(false);
//        listData.get(0).getMeetings().get(1).setNowBarShowed(true);
//        expandableAdapter.notifyDataSetChanged();
        val systemHour: Int = currentDateTimeString.split(":").toTypedArray().get(0).toInt()
        val sysMin: Int = currentDateTimeString.split(":").toTypedArray().get(1).toInt()
        val sysSecond: Int =
            currentDateTimeString.split(":").toTypedArray().get(2).split(" ").toTypedArray().get(0)
                .toInt()
        var minOfHeight = 0
        for (i in items!!.indices) {
            items!!.get(i).meeting?.movePosition = 0
            items!!.get(i).meeting?.nowBarShowed = false
//            for (j in 0 until listData.get(i).getMeetings().size()) {
//                listData.get(i).getMeetings().get(j).setMovePosition(0)
//                listData.get(i).getMeetings().get(j).setNowBarShowed(false)
//            }
            adapter.notifyDataSetChanged()
        }
        for (i in positions.indices) {
//            Log.d(">>>", "getView: " + positions[i].header + positions[i].child)
            if (positions[i].child != 0){
                minOfHeight = 60 / positions[i].child
                val parentHour: Int = positions[i].header!!.split(":").get(0).toInt()
                if (parentHour == systemHour) {
                    smoothScrollToPosition = i

//                countdownSecond = sysMin * 60 + sysSecond;
//                    var view = binding.recyclerView.layoutManager?.findViewByPosition(0)
                    Log.d(">>>", "getTime: " + positions[i].header!! + minOfHeight)
                    var childSize = positions[i].child
                    for (j in 0 until childSize) {
//                        val childItem: View = binding.recyclerView.getChildAt(1)
                        var childItem  = binding.recyclerView.layoutManager?.findViewByPosition(i+j)
                        Log.d(">>>", "getView: " + childItem)
                        if (j == sysMin / minOfHeight) {
//                            Log.d(">>>", "getChildView:: if it's null >>> $childItem")
                            if (childItem == null) {
                                return
                            }
                            Log.d(">>>", "getChildView:: " + childItem + childItem.measuredHeight)
                            val nowLabel = childItem.findViewById<LinearLayout>(R.id.fl_now)
                            if (nowLabel != null) {
                                maxChildHeight = childItem.measuredHeight - nowLabel.measuredHeight
                                Log.d(">>>", "getChildView: NOW bar : " + nowLabel.measuredHeight)
                            }
                            var minNow: Int
                            if (sysMin == minOfHeight - 1) {
                                minNow = sysMin + 1
                            } else {
                                minNow = sysMin - minOfHeight * (sysMin / minOfHeight)
                                if (minNow == minOfHeight - 1) {
                                    minNow = minNow + 1
                                }
                            }
                            Log.d(">>>", "get: min now >> $minNow")
                            position = minNow * maxChildHeight / minOfHeight
                            if (position < 0) {
                                position = position * -1
                            }
                            Log.d(">>>", "get: $i ${childSize}  $position")
                            items!![(i+1)+j].meeting?.nowBarShowed = true
                            items!![(i+1)+j].meeting?.movePosition = position
                            adapter.notifyDataSetChanged()
//                        listData.get(i).getMeetings().get(j).setMovePosition(position as Int)
//                        listData.get(i).getMeetings().get(j).setNowBarShowed(true)
//                        expandableAdapter.notifyDataSetChanged()
                            break
                        }
                        Log.d(">>>",
                            "getChildView: " + items!![j].meeting?.nowBarShowed + items!![j].meeting?.movePosition
                        )
                    }
                }
            }
        }
    }

    private fun initUI() {

        val windowBackground = window.decorView.background

        binding.blurLayout.setupWith(binding.root)
            .setFrameClearDrawable(windowBackground)
            ?.setBlurAlgorithm(SupportRenderScriptBlur(this))
            ?.setBlurRadius(25f)
            ?.setBlurAutoUpdate(true)
            ?.setHasFixedTransformationMatrix(true)

        dialog = ProgressDialog(this)
        adapter = HomeScreenAdapter(viewModel.cities.value ?: emptyList())
        adapter.delegate = object : CityViewHolder.Delegate {
            override fun onItemClick(city: String, view: View) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                intent.putExtra(CITY_ID, city)
                startActivity(intent)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecorator = DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider_default)!!)
        binding.recyclerView.addItemDecoration(itemDecorator)

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

    private val homeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d(">>>", ": ${result.data?.getStringExtra("update")}")
        if (result.resultCode == RESULT_OK){
            result.data?.getStringExtra("update")?.let { viewModel.setTextLabel(it) }
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onResume() {
        super.onResume()
        viewModel.queryCityList(CITY_NAME_PREFIX)
//        viewModel.getReservationList()
    }

    override fun setUpViewModel() {
        viewModel.cities.observe(this) {
//            Log.d(">>", "List updated")
            binding.recyclerView.adapter = adapter
            adapter.updateData(it)
        }

//        viewModel.reservations.observe(this) {
//
//            var hasNoMeeting = true
//            adapter = RecyclerViewAdapter()
//            items = ArrayList()
//            for (j in timeHeader().indices) {
//                val time = timeHeader()[j]
//                items?.add(Item(0, time, null))
//                val position = Position()
//                position.header = time
//                var k = 0
//                for (i in it.indices) {
//                    val reservation = it[i]
//                    var reservationTime: Int =
//                        it[i].start_time?.split(":")!![0].toInt()
//                    if (reservationTime > 12) {
//                        reservationTime %= 12
//                    }
//
//                    if (time.split(" ").toTypedArray()[0].split(":")
//                            .toTypedArray()[0].toInt() == reservationTime
//                    ) {
//                        k++
//                        position.child = k
//                        items?.add(Item(1, "", Meeting(
//                            reservation.color!!,
//                            reservation.room?.name,
//                            reservation.agenda,
//                            false,
//                            0,
//                            reservation.start_time,
//                            reservation.end_time
//                        )))
//                    }
//                }
//                if (items!![items!!.size - 1].itemType == 0) {
//                    items?.add(Item(1, time, null))
//                }
//                positions.add(position)
//                Log.d(">>>", "setUpViewModel: ${position.header} ${position.child}")
//            }
//            Log.d(">>", "prepareData List updated $items")
//            adapter.setItems(items!!)
//            binding.recyclerView.adapter = adapter
////            adapter.setItems(it)
//
//            clock();
////            prepareData(it)
//        }
    }

    private fun timeHeader(): Array<String>{
        return arrayOf(
            "7:00 AM",
            "8:00 AM",
            "9:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 AM",
            "1:00 PM",
            "2:00 PM",
            "3:00 PM",
            "4:00 PM",
            "5:00 PM",
            "6:00 PM",
            "7:00 PM",
        )
    }

    private fun prepareData(list: List<Reservation>) {
        val items = ArrayList<Data>()
        for (j in timeHeader().indices) {
            val time = timeHeader()[j]
            val meetings = ArrayList<Meeting>()
            for (i in list.indices) {
                val reservation = list[i]
                var reservationTime: Int =
                    list[i].start_time?.split(":")!![0].toInt()
                val reservationMin: Int =
                    list[i].start_time?.split(":")!![1].toInt()
                if (reservationTime > 12) {
                    reservationTime %= 12
                }
                if (time.split(" ").toTypedArray()[0].split(":")
                        .toTypedArray()[0].toInt() == reservationTime && time.split(" ")
                        .toTypedArray()[0].split(":").toTypedArray()[1].toInt() < reservationMin
                ) {

                    meetings.add(Meeting(
                        reservation.color!!,
                        reservation.room?.name,
                        reservation.room?.name,
                        false,
                        0
                    ))

                }
            }
            items.add(Data(time, meetings))
        }
        Log.d(">>", "prepareData >>> $items")
//        adapter.setItems(items)
//        adapter.notifyDataSetChanged()
    }

}