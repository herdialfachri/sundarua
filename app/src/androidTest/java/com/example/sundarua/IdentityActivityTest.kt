package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sundarua.activity.IdentityActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IdentityActivityTest {

    private lateinit var context: Context

    private fun delay(ms: Long = 1500L) {
        Thread.sleep(ms)
    }

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().clear().commit()
    }

    @Test
    fun whenNameIsValid_intentToMainActivity() {
        ActivityScenario.launch(IdentityActivity::class.java)

        onView(withId(R.id.nameEditText)).perform(typeText("Ujang"))
        delay()
        closeSoftKeyboard()

        onView(withId(R.id.saveNameButton)).perform(click())
        delay()
    }

    @Test
    fun whenNameWithSymbolIsValid_intentToMainActivity() {
        ActivityScenario.launch(IdentityActivity::class.java)

        onView(withId(R.id.nameEditText)).perform(typeText("Ujang!23"))
        delay()
        closeSoftKeyboard()

        onView(withId(R.id.saveNameButton)).perform(click())
        delay()
    }

    @Test
    fun whenNameIsEmpty_showsError() {
        ActivityScenario.launch(IdentityActivity::class.java)

        onView(withId(R.id.nameEditText)).perform(typeText("           "))
        delay()
        closeSoftKeyboard()

        onView(withId(R.id.saveNameButton)).perform(click())
        delay()

        onView(withId(R.id.nameEditText)).check(matches(hasErrorText("Nami henteu kénging kosong")))
        delay()
    }
}