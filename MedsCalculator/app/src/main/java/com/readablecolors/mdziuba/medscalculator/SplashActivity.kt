package com.readablecolors.mdziuba.medscalculator

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Redirects to main activity
        //This is done to show a splash screen image, while the app is starting up, and then
        //show functional user interface, once the app is ready
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
