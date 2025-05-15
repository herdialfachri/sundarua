package com.example.sundarua

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.QuizActivity
import com.example.sundarua.activity.StartQuizActivity
import com.example.sundarua.model.QuestionModel
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartQuizActivityTest {

    @Before
    fun setUp() {
        StartQuizActivity.Companion.time = "1"
        StartQuizActivity.Companion.questionModelList = listOf(
            QuestionModel(
                question = "Sato naon ngaranna harimau?",
                options = listOf("Macan", "Hayam", "Ucing", "Maung"),
                correct = "Macan"
            ),
            QuestionModel(
                question = "Ibukota Jawa Barat?",
                options = listOf("Bogor", "Bekasi", "Bandung", "Tasik"),
                correct = "Bandung"
            )
        )

        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testCompleteQuizWithCorrectAnswers() {
        // Meluncurkan aktivitas
        ActivityScenario.launch(StartQuizActivity::class.java)

        val questions = StartQuizActivity.Companion.questionModelList

        // Pilih jawaban benar
        for (i in questions.indices) {
            val selectedAnswer = questions[i].correct // Pilih jawaban benar

            // Pilih jawaban yang benar
            onView(allOf(withText(selectedAnswer), isClickable())).perform(click())

            // Tambah delay sebelum klik next (simulasi waktu mikir)
            Thread.sleep(3000)

            onView(withId(R.id.next_btn)).perform(click())
        }

        // Tambah delay supaya dialog hasil muncul
        Thread.sleep(1500)

        // Verifikasi skor muncul (harus 100)
        onView(withId(R.id.score_progress_indicator))
            .check(matches(isDisplayed()))

        // Delay sebelum klik selesai
        Thread.sleep(1000)

        onView(withId(R.id.finish_btn)).perform(click())

        // Verifikasi pindah ke QuizActivity
        Intents.intended(hasComponent(QuizActivity::class.java.name))
        Thread.sleep(3000)
    }

    @Test
    fun testCompleteQuizWithIncorrectAnswers() {
        // Meluncurkan aktivitas
        ActivityScenario.launch(StartQuizActivity::class.java)

        val questions = StartQuizActivity.Companion.questionModelList

        // Pilih jawaban salah
        for (i in questions.indices) {
            val incorrectAnswer = questions[i].options.first { it != questions[i].correct } // Pilih jawaban salah

            // Pilih jawaban yang salah
            onView(allOf(withText(incorrectAnswer), isClickable())).perform(click())

            // Tambah delay sebelum klik next (simulasi waktu mikir)
            Thread.sleep(3000)

            onView(withId(R.id.next_btn)).perform(click())
        }

        // Tambah delay supaya dialog hasil muncul
        Thread.sleep(1500)

        // Verifikasi skor muncul (harus 0)
        onView(withId(R.id.score_progress_indicator))
            .check(matches(isDisplayed()))

        // Delay sebelum klik selesai
        Thread.sleep(1000)

        onView(withId(R.id.finish_btn)).perform(click())

        // Verifikasi pindah ke QuizActivity
        Intents.intended(hasComponent(QuizActivity::class.java.name))
        Thread.sleep(3000)
    }
}