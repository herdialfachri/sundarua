package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.MainActivity
import com.example.sundarua.activity.QuizActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizActivityTest {

    private lateinit var sharedPref: SharedPreferences

    @Before
    fun setUp() {
        init()

        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("user_name", "Test User")
            .commit()
    }

    @After
    fun tearDown() {
        release()

        sharedPref.edit().clear().commit()
    }

    @Test
    fun testBackToMainActivityWithDelay() {
        ActivityScenario.launch(QuizActivity::class.java)
        Thread.sleep(2000)

        onView(withId(R.id.back_main_btn)).perform(click())

        Thread.sleep(2000)

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun testClickQuizItem_ShowsDialog() {
        ActivityScenario.launch(QuizActivity::class.java)

        Thread.sleep(2000)

        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
    }
}