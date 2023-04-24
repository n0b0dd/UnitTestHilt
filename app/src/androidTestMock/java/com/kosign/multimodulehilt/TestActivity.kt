package com.kosign.multimodulehilt

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kosign.multimodulehilt.view.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class TestActivity {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun clickItemAndOpenDetail() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        SystemClock.sleep(1000)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
        pressBack()
        SystemClock.sleep(1000)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()));
        pressBack()
        SystemClock.sleep(1000)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()));
        activityScenario.close()
    }

    @Test
    fun testPutExtraIntent(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        SystemClock.sleep(1000)
//        Espresso.onView(withId(R.id.container))
//            .perform(ViewActions.click())
//        SystemClock.sleep(1000)
//
//        Espresso.onView(withId(R.id.ed_update)).perform(
//            ViewActions.clearText(),
//            ViewActions.typeText("Some Text")
//        )
//        Espresso.onView(withId(R.id.button))
//            .perform(ViewActions.click())
        //back pressed home activity
//        pressBack()
        SystemClock.sleep(1000)
        activityScenario.close()
    }

}