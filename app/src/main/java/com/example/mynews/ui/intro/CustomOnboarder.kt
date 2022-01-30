package com.example.mynews.ui.intro

import android.os.Bundle
import com.example.mynews.MainActivity
import android.content.Intent
import com.example.mynews.R
import com.github.paolorotolo.appintro.AppIntro2

class CustomOnboarder : AppIntro2() {
    override fun init(savedInstanceState: Bundle?) {

        addSlide(SampleSlide.newInstance(R.layout.intro_1))
        addSlide(SampleSlide.newInstance(R.layout.intro_2))
        addSlide(SampleSlide.newInstance(R.layout.intro_3))
    }

    private fun loadMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onNextPressed() {}

    override fun onDonePressed() { finish() }

    override fun onSlideChanged() {}
}