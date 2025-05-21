package com.example.sundarua

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.QuizActivity
import com.example.sundarua.activity.StartQuizActivity
import com.example.sundarua.model.QuestionModel
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartQuizActivityTest {

    private fun delay(ms: Long = 1000L) {
        Thread.sleep(ms)
    }

    private val questionList = listOf(
        QuestionModel("What is A?", listOf("A1", "A2", "A3", "A4"), "A1"),
        QuestionModel("What is B?", listOf("B1", "B2", "B3", "B4"), "B1")
    )

    @Before
    fun setup() {
        StartQuizActivity.Companion.questionModelList = questionList
        StartQuizActivity.Companion.time = "1"
    }

    @After
    fun tearDown() {
        StartQuizActivity.Companion.questionModelList = listOf()
        StartQuizActivity.Companion.time = ""
    }

    @Test
    fun testQuiz_correctAnswer() {
        init()
        val scenario = ActivityScenario.launch(StartQuizActivity::class.java)

        onView(withId(R.id.question_textview))
            .check(matches(withText("What is A?")))
        delay()

        onView(withId(R.id.btn0)).perform(click())
        delay()

        onView(withId(R.id.next_btn)).perform(click())
        delay()

        onView(withId(R.id.question_textview))
            .check(matches(withText("What is B?")))
        delay()

        onView(withId(R.id.btn0)).perform(click())
        delay()

        onView(withId(R.id.next_btn)).perform(click())
        delay()

        onView(withText(containsString("Ngiring bingah! hade pisan")))
            .check(matches(isDisplayed()))
        delay()

        onView(withText("Rengse")).perform(click())
        delay()

        intended(hasComponent(QuizActivity::class.java.name))
        delay()

        scenario.close()

        release()
    }

    @Test
    fun testQuiz_wrongAnswer() {
        init()
        val scenario = ActivityScenario.launch(StartQuizActivity::class.java)

        onView(withId(R.id.question_textview))
            .check(matches(withText("What is A?")))
        delay()

        onView(withId(R.id.btn2)).perform(click())
        delay()

        onView(withId(R.id.next_btn)).perform(click())
        delay()

        onView(withId(R.id.question_textview))
            .check(matches(withText("What is B?")))
        delay()

        onView(withId(R.id.btn2)).perform(click())
        delay()

        onView(withId(R.id.next_btn)).perform(click())
        delay()

        onView(withText(containsString("Hayu diajar deui sing rajin")))
            .check(matches(isDisplayed()))
        delay()

        onView(withText("Rengse")).perform(click())
        delay()

        intended(hasComponent(QuizActivity::class.java.name))
        delay()

        scenario.close()
        release()
    }

    @Test
    fun testWithoutAnswer_showsWarning() {
        val scenario = ActivityScenario.launch(StartQuizActivity::class.java)
        onView(withId(R.id.question_textview))
            .check(matches(withText("What is A?")))
        delay()

        onView(withId(R.id.next_btn)).perform(click())
        delay()

        scenario.close()
    }
}