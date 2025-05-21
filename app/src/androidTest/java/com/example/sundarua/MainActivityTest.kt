package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sundarua.activity.IdentityActivity
import com.example.sundarua.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var context: Context

    private fun delay(ms: Long = 1500L) {
        Thread.sleep(ms)
    }

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().clear().commit()

        val gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit().clear().commit()
    }

    @Test
    fun whenUserNameExists_showsGreeting() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        val gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit().putInt("coin", 100).putInt("level", 5).commit()

        ActivityScenario.launch(MainActivity::class.java)
        delay()

        onView(withId(R.id.greeting))
            .check(matches(withText("Sampurasun, Ujang!")))

        onView(withId(R.id.coinTv))
            .check(matches(withText("Coin: 100")))

        onView(withId(R.id.levelTv))
            .check(matches(withText("Level: 5")))
        delay()
    }

    @Test
    fun whenUserNameNotExists_intentToIdentityActivity() {
        Intents.init()

        ActivityScenario.launch(MainActivity::class.java)
        delay()

        Intents.intended(hasComponent(IdentityActivity::class.java.name))
        delay()

        Intents.release()
    }

    @Test
    fun testIntent_toWordActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)
        delay()

        onView(withId(R.id.toWordBtn)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.WordActivity"))
        delay()

        Intents.release()
    }

    @Test
    fun testIntent_toQuizActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)
        delay()

        onView(withId(R.id.toQuizBtn)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.QuizActivity"))
        delay()

        Intents.release()
    }

    @Test
    fun testIntent_toAksaraActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)
        delay()

        onView(withId(R.id.toAksaraBtn)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.AksaraActivity"))
        delay()

        Intents.release()
    }

    @Test
    fun testIntent_toRewardActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)
        delay()

        onView(withId(R.id.coinTv)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.RewardActivity"))
        delay()

        Intents.release()
    }

    @After
    fun tearDown() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().clear().commit()

        val gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit().clear().commit()
    }
}