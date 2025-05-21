package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.RewardActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RewardActivityTest {

    private lateinit var context: Context

    private fun delay(ms: Long = 1500L) {
        Thread.sleep(ms)
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
            .edit().putInt("coin", 300).apply()

        context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    @After
    fun cleanup() {
        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
            .edit().clear().apply()
        context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    @Test
    fun testClaimBookReward() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getBookBtn)).perform(click())
        delay()

        onView(withText("Leres")).perform(click())
        delay()

        onView(withText(containsString("Buku - Kode:"))).check(matches(isDisplayed()))
        delay()
    }

    @Test
    fun testClaimPencilReward() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getPencilBtn)).perform(click())
        delay()

        onView(withText("Leres")).perform(click())
        delay()

        onView(withText(containsString("Pensil - Kode:"))).check(matches(isDisplayed()))
        delay()
    }

    @Test
    fun testClaimInsufficientCoin() {
        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
            .edit().putInt("coin", 50).apply()

        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getBookBtn)).perform(click())
        delay()

        onView(withText("Leres")).perform(click())
        delay()

        onView(withText("Teu acan aya hadiah")).check(matches(isDisplayed()))
        delay()
    }
}