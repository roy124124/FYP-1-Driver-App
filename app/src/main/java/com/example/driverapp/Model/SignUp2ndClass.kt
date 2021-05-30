package com.example.driverapp.Model

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.*
import java.util.*

class SignUp2ndClass : AppCompatActivity() {

    var backbtn: ImageView? = null
    var nextBtn: Button? = null
    var Title: TextView? = null
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    var datePicker: DatePicker? = null
    var getfName: String? = ""
    var getEmail: String? = ""
    var getHAddress: String? = ""
    var getPassword: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up2nd_class)
//
//        backbtn = findViewById(R.id.signup_back_button)
//        nextBtn = findViewById((R.id.next1_btn))
//        Title = findViewById((R.id.signup_title))
//        radioGroup = findViewById((R.id.Gender))
//        datePicker = findViewById((R.id.Age))
        getfName = intent.getStringExtra("fname")
        getEmail = intent.getStringExtra("email")
        getHAddress = intent.getStringExtra("hAddress")
        getPassword = intent.getStringExtra("password")

    }

    fun callNextScreen(view: View) {

        if (!validateAge() or !validateGender()) {
            return
        }

        radioButton = findViewById(radioGroup!!.checkedRadioButtonId)
        val gender: String = " " + radioButton.text.toString();


        val day = datePicker!!.dayOfMonth
        val month = datePicker!!.month
        val year = datePicker!!.year
        val date = "$day/$month/$year"


        val intent = Intent(this@SignUp2ndClass, Sign_up_3rdClass::class.java)

//        val getfName = intent.getStringExtra("fname")
//        val getEmail = intent.getStringExtra("email")
//        val getHAddress = intent.getStringExtra("hAddress")
//        val getIAddress = intent.getStringExtra("iAddress")
//        val getPassword = intent.getStringExtra("password")


        val options = ActivityOptions.makeSceneTransitionAnimation(
            this, Pair.create(backbtn, "transition_back_button"),
            Pair.create(Title, "transition_crrate_button"),
            Pair.create(nextBtn, "transition_next_button")
        )

        intent.putExtra("fname", getfName)
        intent.putExtra("email", getEmail)
        intent.putExtra("hAddress", getHAddress)
        intent.putExtra("password", getPassword)
        intent.putExtra("age", date)
        intent.putExtra("gender", gender)

        startActivity(intent, options.toBundle())

    }

    private fun validateGender(): Boolean {
        return if (radioGroup!!.checkedRadioButtonId === -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun validateAge(): Boolean {
        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        val userAge = datePicker!!.year
        val isAgeValid = currentYear - userAge
        return if (isAgeValid < 20 || isAgeValid > 50) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show()
            false
        } else true
    }
    fun callBack(view: View) {
        val intent = Intent(applicationContext, Signup::class.java)
        startActivity(intent)

    }
}