package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.MainActivity
import com.example.sundarua.activity.RewardActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RewardActivityTest {

    private lateinit var userPref: SharedPreferences
    private lateinit var gamePref: SharedPreferences
    private lateinit var rewardPref: SharedPreferences

    @Before
    fun setUp() {
        init()

        val context = ApplicationProvider.getApplicationContext<Context>()

        // SharedPreferences untuk user info
        userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit()
            .putString("user_name", "Test User")
            .apply()

        // SharedPreferences untuk game data
        gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit()
            .putInt("coin", 100)
            .apply()

        // SharedPreferences untuk reward claim history
        rewardPref = context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
        rewardPref.edit()
            .putStringSet("claim_history", emptySet())
            .apply()
    }

    @After
    fun tearDown() {
        release()

        userPref.edit().clear().apply()
        gamePref.edit().clear().apply()
        rewardPref.edit().clear().apply()
    }

    @Test
    fun testBackToMainActivity() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.back_main_btn)).perform(click())
        intended(hasComponent(MainActivity::class.java.name))
        Thread.sleep(2000)
    }

    @Test
    fun testClickLeresBookWillDismissDialog() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getBookBtn)).perform(click())
        onView(withText("Leres")).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun testClickSanesBookWillDismissDialog() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getBookBtn)).perform(click())
        onView(withText("Sanes")).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun testClickLeresPencilWillDismissDialog() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getPencilBtn)).perform(click())
        onView(withText("Leres")).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun testClickSanesPencilWillDismissDialog() {
        ActivityScenario.launch(RewardActivity::class.java)
        onView(withId(R.id.getPencilBtn)).perform(click())
        onView(withText("Sanes")).perform(click())
        Thread.sleep(3000)
    }
}