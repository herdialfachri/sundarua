package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.RewardActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RewardActivityTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        // Simulasikan user memiliki cukup koin
        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
            .edit().putInt("coin", 300).apply()

        // Bersihkan riwayat sebelumnya
        context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    @After
    fun cleanup() {
        // Bersihkan setelah test
        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
            .edit().clear().apply()
        context.getSharedPreferences("reward_data", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    @Test
    fun testClaimBookReward() {
        ActivityScenario.launch(RewardActivity::class.java)

        // Klik tombol "Buku"
        onView(withId(R.id.getBookBtn)).perform(click())

        // Klik "Leres" di dialog
        onView(withText("Leres")).perform(click())

        // Tunggu sejenak agar UI sempat update
        Thread.sleep(3000)

        // Cek apakah history menampilkan entri reward
        onView(withText(containsString("Buku - Kode:"))).check(matches(isDisplayed()))
    }

    @Test
    fun testClaimPencilReward() {
        ActivityScenario.launch(RewardActivity::class.java)

        // Klik tombol "Buku"
        onView(withId(R.id.getPencilBtn)).perform(click())

        // Klik "Leres" di dialog
        onView(withText("Leres")).perform(click())

        // Tunggu sejenak agar UI sempat update
        Thread.sleep(3000)

        // Cek apakah history menampilkan entri reward
        onView(withText(containsString("Pensil - Kode:"))).check(matches(isDisplayed()))
    }

    @Test
    fun testClaimInsufficientCoin() {
        // Set coin hanya 50
        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
            .edit().putInt("coin", 50).apply()

        ActivityScenario.launch(RewardActivity::class.java)

        // Klik tombol "Buku"
        onView(withId(R.id.getBookBtn)).perform(click())

        // Klik "Leres" di dialog
        onView(withText("Leres")).perform(click())

        Thread.sleep(3000)

        // Cek bahwa tidak ada reward yang diklaim
        onView(withText("Teu acan aya hadiah")).check(matches(isDisplayed()))
    }
}