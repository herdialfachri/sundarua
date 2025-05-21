package com.example.sundarua

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.sundarua.activity.MainActivity
import com.example.sundarua.activity.QuizActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class QuizActivityTest {

    // Delay helper
    private fun delay(ms: Long = 3000L) {
        Thread.sleep(ms)
    }

    @Before
    fun setupIntents() {
        Intents.init()
    }

    @After
    fun tearDownIntents() {
        Intents.release()
    }

    @Test
    fun testBackButtonNavigatesToMainActivity() {
        ActivityScenario.launch(QuizActivity::class.java)

        onView(withId(R.id.back_main_btn)).perform(click())
        delay()

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun testClickRecyclerViewItem_showsDialog() {
        ActivityScenario.launch(QuizActivity::class.java)

        delay() // Tunggu data tampil

        // Klik item pertama di RecyclerView
        onView(withId(R.id.recycler_view)) // Ganti dengan ID RecyclerView-mu
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        delay()
    }
}