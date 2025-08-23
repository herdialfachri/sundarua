package com.example.sundarua.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.sundarua.R

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val sliderAllImages = intArrayOf(
        R.drawable.onboarding1,
        R.drawable.onboarding2,
        R.drawable.onboarding3
    )

    private val sliderAllTitle = intArrayOf(
        R.string.screen1desc,
        R.string.screen2desc,
        R.string.screen3desc
    )

    private val sliderAllDesc = intArrayOf(
        R.string.screen1,
        R.string.screen1,
        R.string.screen1
    )

    override fun getCount(): Int {
        return sliderAllTitle.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_screen, container, false)

        val sliderImage = view.findViewById<ImageView>(R.id.sliderImage)
        val sliderTitle = view.findViewById<TextView>(R.id.sliderTitle)
        val sliderDesc = view.findViewById<TextView>(R.id.sliderDesc)

        sliderImage.setImageResource(sliderAllImages[position])
        sliderTitle.setText(sliderAllTitle[position])
        sliderDesc.setText(sliderAllDesc[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
