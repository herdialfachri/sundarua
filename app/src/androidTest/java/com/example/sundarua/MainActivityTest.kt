package com.example.sundarua

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sundarua.activity.IdentityActivity
import com.example.sundarua.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        // Bersihkan SharedPreferences sebelum test
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().clear().commit()

        val gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit().clear().commit()
    }

    @Test
    fun whenUserNameExists_showsGreetingAndGameData() {
        // Setup SharedPreferences user dan game data
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        val gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit().putInt("coin", 100).putInt("level", 5).commit()

        // Launch MainActivity
        ActivityScenario.launch(MainActivity::class.java)

        // Cek greeting text
        onView(withId(R.id.greeting))
            .check(matches(withText("Sampurasun, Ujang!")))

        // Cek coin text
        onView(withId(R.id.coinTv))
            .check(matches(withText("Coin: 100")))

        // Cek level text
        onView(withId(R.id.levelTv))
            .check(matches(withText("Level: 5")))
    }

    @Test
    fun whenUserNameNotExists_redirectsToIdentityActivity() {
        // User prefs sudah kosong dari setUp()

        Intents.init()

        ActivityScenario.launch(MainActivity::class.java)

        // Verifikasi intent ke IdentityActivity
        Intents.intended(hasComponent(IdentityActivity::class.java.name))

        Intents.release()
    }

    @Test
    fun testNavigation_toWordActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.toWordBtn)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.WordActivity"))

        Intents.release()
    }

    @Test
    fun testNavigation_toQuizActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.toQuizBtn)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.QuizActivity"))

        Intents.release()
    }

    @Test
    fun testNavigation_toAksaraActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.toAksaraBtn)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.AksaraActivity"))

        Intents.release()
    }

    @Test
    fun testNavigation_toRewardActivity() {
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().putString("user_name", "Ujang").commit()

        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.coinTv)).perform(click())
        Intents.intended(hasComponent("com.example.sundarua.activity.RewardActivity"))

        Intents.release()
    }

    @After
    fun tearDown() {
        // Bersihkan SharedPreferences setelah test (opsional)
        val userPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userPref.edit().clear().commit()

        val gamePref = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        gamePref.edit().clear().commit()
    }
}