package com.example.sundarua

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.sundarua.activity.WordActivity
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class WordActivityTest {

    private fun delay(ms: Long = 1500L) {
        Thread.sleep(ms)
    }

    @Test
    fun testDisplayedSearchView() {
        ActivityScenario.launch(WordActivity::class.java)
        delay()

        onView(withId(R.id.searchView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDisplayedRecyclerView() {
        ActivityScenario.launch(WordActivity::class.java)
        delay()

        onView(withId(R.id.rvWords))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testScrolledRecyclerView() {
        ActivityScenario.launch(WordActivity::class.java)
        delay()

        onView(withId(R.id.rvWords))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(30))

        onView(withId(R.id.rvWords))
            .check(matches(isDisplayed()))
        delay()
    }

    @Test
    fun testBackButtonToMainActivity() {
        ActivityScenario.launch(WordActivity::class.java)

        onView(withId(R.id.back_main_btn)).perform(click())
        delay()
    }

    @Test
    fun testFilteringSearchView() {
        ActivityScenario.launch(WordActivity::class.java)
        delay()

        onView(withId(R.id.searchView)).perform(click())
        delay()

        val query = "anak"

        onView(allOf(isAssignableFrom(EditText::class.java)))
            .perform(typeText(query), closeSoftKeyboard())
        delay()

        onView(withId(R.id.rvWords))
            .check(matches(hasDescendant(withText(containsString(query)))))
    }
}