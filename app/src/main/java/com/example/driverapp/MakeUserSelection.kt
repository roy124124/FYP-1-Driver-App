package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate

class MakeUserSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_make_user_selection)
    }

    fun callLoginScreen(View: View?) {
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
    }

    fun callSignUpScreen(view: View) {
        val intent = Intent(applicationContext, Signup::class.java)
        startActivity(intent)
    }
}