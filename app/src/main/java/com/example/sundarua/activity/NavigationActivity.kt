package com.example.sundarua.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager.widget.ViewPager
import com.example.sundarua.R
import com.example.sundarua.adapter.ViewPagerAdapter

class NavigationActivity : AppCompatActivity() {

    private lateinit var slideViewPager: ViewPager
    private lateinit var dotIndicator: LinearLayout
    private lateinit var dots: Array<TextView?>
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var buttonGuru: Button
    private lateinit var buttonMurid: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        slideViewPager = findViewById(R.id.slideViewPager)
        dotIndicator = findViewById(R.id.dotIndicator)

        viewPagerAdapter = ViewPagerAdapter(this)
        slideViewPager.adapter = viewPagerAdapter

        // Tambahkan listener untuk update dot
        slideViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                setDotIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        setDotIndicator(0)

        buttonGuru = findViewById(R.id.guruBtn)
        buttonMurid = findViewById(R.id.muridBtn)

        buttonMurid.setOnClickListener {
            val intent = Intent(this, IdentityActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setDotIndicator(position: Int) {
        dots = arrayOfNulls(3)
        dotIndicator.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this).apply {
                text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
                textSize = 35f
                setTextColor(resources.getColor(R.color.very_light_gray, applicationContext.theme))
            }
            dotIndicator.addView(dots[i])
        }
        dots[position]?.setTextColor(resources.getColor(R.color.main_red, applicationContext.theme))
    }

    private fun getItem(i: Int): Int {
        return slideViewPager.currentItem + i
    }
}
