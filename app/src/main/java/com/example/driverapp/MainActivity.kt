package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var topAnim: Animation? = null
    var bottomAnim: Animation? = null
    var image: ImageView? = null
    var logo: TextView? = null
    var slogan: TextView? = null
    private val SPASH_SCREEN: Long = 2700

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        image = findViewById(R.id.imageView)
        logo = findViewById(R.id.textView1)
        slogan = findViewById(R.id.textView2)

        image!!.setAnimation(topAnim)
        logo!!.setAnimation(bottomAnim)
        slogan!!.setAnimation(bottomAnim)


        Handler().postDelayed(
            {
//                val sessionManager =
//                    SessionManager(this@Splash_Screen, SessionManager.SESSION_REMEMBERME)
//                if (sessionManager.checkRememberMe()) {
//                    val intent = Intent(this@Splash_Screen, Long::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
                val intent = Intent(applicationContext, MakeUserSelection::class.java)
                startActivity(intent)
                finish()
//                }
            },
            SPASH_SCREEN
        )

    }
}