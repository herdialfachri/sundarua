package com.example.sundarua

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.sundarua.activity.AksaraActivity
import com.example.sundarua.activity.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AksaraActivityTest {

    // Fungsi delay 2 detik
    private fun delay(ms: Long = 2000L) {
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
    fun testProgressBarGoneAndContentVisible() {
        ActivityScenario.launch(AksaraActivity::class.java)

        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.nestedScrollView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        delay()
    }

    @Test
    fun testRecyclerViewSasatoanIsVisibleAfterScroll() {
        ActivityScenario.launch(AksaraActivity::class.java)

        onView(withId(R.id.nestedScrollView))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.recyclerViewSasatoan))
            .perform(scrollTo())

        onView(withId(R.id.recyclerViewSasatoan))
            .check(matches(isDisplayed()))
        delay()
    }

    @Test
    fun testBackButtonIntentToMainActivity() {
        ActivityScenario.launch(AksaraActivity::class.java)

        onView(withId(R.id.back_main_btn)).perform(click())
        delay()

        intended(hasComponent(MainActivity::class.java.name))
    }
}