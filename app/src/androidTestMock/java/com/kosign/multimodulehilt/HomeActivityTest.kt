package com.kosign.multimodulehilt

import android.content.Intent
import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kosign.multimodulehilt.util.CITY_ID
import com.kosign.multimodulehilt.view.HomeActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class HomeActivityTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val intent: Intent
        get() {
            val intent =
                Intent(ApplicationProvider.getApplicationContext(), HomeActivity::class.java)
            intent.putExtra(CITY_ID, "sin")
            return intent
        }

    lateinit var activityScenario: ActivityScenario<HomeActivity>

    @Test
    fun launchHomeActivity() {
        activityScenario = ActivityScenario.launch(intent)
        SystemClock.sleep(3000)

        activityScenario.close()
    }


}