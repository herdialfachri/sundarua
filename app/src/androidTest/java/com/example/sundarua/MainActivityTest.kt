package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.AksaraActivity
import com.example.sundarua.activity.MainActivity
import com.example.sundarua.activity.QuizActivity
import com.example.sundarua.activity.RewardActivity
import com.example.sundarua.activity.WordActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        // Simulasi user sudah mengisi nama
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .edit().putString("user_name", "Tester").commit()

        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun testNavigateToWordActivity() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.toWordBtn)).perform(click())
        Intents.intended(hasComponent(WordActivity::class.java.name))

        Thread.sleep(3000) // Delay 3 detik saat WordActivity terbuka
    }

    @Test
    fun testNavigateToQuizActivity() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.toQuizBtn)).perform(click())
        Intents.intended(hasComponent(QuizActivity::class.java.name))

        Thread.sleep(3000)
    }

    @Test
    fun testNavigateToAksaraActivity() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.toAksaraBtn)).perform(click())
        Intents.intended(hasComponent(AksaraActivity::class.java.name))

        Thread.sleep(3000)
    }

    @Test
    fun testNavigateToRewardActivity() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.coinTv)).perform(click())
        Intents.intended(hasComponent(RewardActivity::class.java.name))

        Thread.sleep(3000)
    }
}