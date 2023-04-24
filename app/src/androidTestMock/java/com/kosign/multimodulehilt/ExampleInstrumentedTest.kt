package com.kosign.multimodulehilt

import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kosign.multimodulehilt.view.MainActivity
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {



    @Before
    fun setUp(){

    }

    @After
    fun tearDown(){

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.kosign.multimodulehilt", appContext.packageName)
    }

    @Test
    fun test(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        SystemClock.sleep(3000)
//        Espresso.onView(withId(R.id.container))
//            .perform(click())

        activityScenario.close()
    }
}