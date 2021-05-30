package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ForgetPasswordSuccessMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_success_message)
    }

    fun backToLogin(view: View) {
        val intent = Intent(applicationContext, Login::class.java)

        startActivity(intent)
        finish()

    }
    fun callBack(view: View) {
        val intent = Intent(applicationContext, Login::class.java)

        startActivity(intent)
        finish()

    }
}