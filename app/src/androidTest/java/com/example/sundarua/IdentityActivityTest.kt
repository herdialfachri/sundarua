package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.IdentityActivity
import com.example.sundarua.activity.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IdentityActivityTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        // Clear shared preferences
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .edit().clear().commit()

        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun testEnterName() {
        val scenario = ActivityScenario.launch(IdentityActivity::class.java)

        // Isi nama
        onView(withId(R.id.nameEditText)).perform(typeText("Tester"), closeSoftKeyboard())

        Thread.sleep(1500)

        // Klik tombol simpan
        onView(withId(R.id.saveNameButton)).perform(click())

        // Tunggu sebentar agar sempat redirect
        Thread.sleep(2000)

        // Cek intent menuju MainActivity
        Intents.intended(hasComponent(MainActivity::class.java.name))

        scenario.close()
    }

    @Test
    fun testEnterNameWithSpaceNumberSymbol() {
        val scenario = ActivityScenario.launch(IdentityActivity::class.java)

        // Isi nama
        onView(withId(R.id.nameEditText)).perform(typeText("Tester !23"), closeSoftKeyboard())

        Thread.sleep(1500)

        // Klik tombol simpan
        onView(withId(R.id.saveNameButton)).perform(click())

        // Tunggu sebentar agar sempat redirect
        Thread.sleep(2000)

        // Cek intent menuju MainActivity
        Intents.intended(hasComponent(MainActivity::class.java.name))

        scenario.close()
    }

    @Test
    fun testEmptyNameShowsError() {
        val scenario = ActivityScenario.launch(IdentityActivity::class.java)

        Thread.sleep(1500)

        // Klik langsung tombol tanpa isi nama
        onView(withId(R.id.saveNameButton)).perform(click())

        // Pastikan error muncul di EditText
        onView(withId(R.id.nameEditText))
            .check(matches(hasErrorText("Nami henteu kénging kosong")))

        scenario.close()
    }
}