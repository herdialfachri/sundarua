package com.example.sundarua

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sundarua.activity.AksaraActivity
import com.example.sundarua.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AksaraActivityTest {

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
        // Meluncurkan aktivitas AksaraActivity
        ActivityScenario.launch(AksaraActivity::class.java)

        // Menunggu beberapa detik setelah aktivitas dimulai untuk memverifikasi tampilan
        Thread.sleep(2000)

        // Klik tombol kembali ke MainActivity
        onView(withId(R.id.back_main_btn)).perform(click())

        // Tambahkan delay 2 detik untuk mensimulasikan waktu tunggu saat transisi aktivitas
        Thread.sleep(2000)

        // Verifikasi bahwa aplikasi kembali ke MainActivity
        Intents.intended(hasComponent(MainActivity::class.java.name))
    }
}