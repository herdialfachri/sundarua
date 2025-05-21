package com.example.sundarua

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.sundarua.activity.QuizActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class QuizActivityTest {

    private fun delay(ms: Long = 1500L) {
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
    fun testBackButtonToMainActivity() {
        ActivityScenario.launch(QuizActivity::class.java)

        onView(withId(R.id.back_main_btn)).perform(click())
        delay()
    }

    @Test
    fun testClickRecyclerView_showsDialog() {
        ActivityScenario.launch(QuizActivity::class.java)
        delay()

        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        delay()
    }
}