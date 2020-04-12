package com.example.hungrazy.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.viewpager.widget.ViewPager
import com.example.hungrazy.MainActivity
import com.example.hungrazy.PrefConstant
import com.example.hungrazy.R


class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick , OnBoardingTwoFragment.OnOptionClick {

    lateinit var viewPager: ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindView()
        setupSharedPreference()
    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem =1
    }

    override fun onOptionBack() {
        viewPager.currentItem =0
    }

    override fun onOptionDone() {
        //2nd fragment
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY, true)
        editor.apply()

        val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
