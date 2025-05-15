package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.MainActivity
import com.example.sundarua.activity.WordActivity
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

@RunWith(AndroidJUnit4::class)
class WordActivityTest {

    private lateinit var sharedPref: SharedPreferences

    @Before
    fun setUp() {
        init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Isi sharedPref agar tidak redirect ke IdentityActivity
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
        // Launch WordActivity
        ActivityScenario.launch(WordActivity::class.java)
        Thread.sleep(2000)

        // Klik tombol back
        onView(withId(R.id.back_main_btn)).perform(click())

        // Tunggu transisi intent
        Thread.sleep(2000)

        // Pastikan kembali ke MainActivity
        Intents.intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun testSearchWord_sleep() {
        // Launch activity
        ActivityScenario.launch(WordActivity::class.java)
        Thread.sleep(1500)

        // Klik SearchView agar fokus
        onView(withId(R.id.searchView)).perform(click())
        Thread.sleep(500)

        // Temukan EditText di dalam SearchView dan ketik "sleep"
        onView(allOf(isAssignableFrom(EditText::class.java)))
            .perform(typeText("sleep"), closeSoftKeyboard())

        Thread.sleep(2000)

        // Verifikasi hasil muncul
        onView(withId(R.id.rvWords))
            .check(matches(hasDescendant(withText(containsString("sleep")))))
    }
}