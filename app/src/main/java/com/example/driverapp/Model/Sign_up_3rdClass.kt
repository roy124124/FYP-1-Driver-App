package com.example.driverapp.Model

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Sign_up_3rdClass : AppCompatActivity() {

    var backbtn: ImageView? = null
    var nextBtn: Button? = null
    var Title: TextView? = null
    var ph_number: TextInputLayout? = null


    var getfName: String? = ""
    var getEmail: String? = ""
    var getHAddress: String? = ""
    var getPassword: String? = ""
    var getAge: String? = ""
    var getGender: String? = ""
    var _id: Int = 0
    var phone_arrayList = ArrayList<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up3rd_class)
//
//        backbtn = findViewById(R.id.signup_back_button)
//        nextBtn = findViewById((R.id.next1_btn))
//        Title = findViewById((R.id.signup_title))
//        ph_number = findViewById((R.id.ph_number))

        getfName = intent.getStringExtra("fname")
        getEmail = intent.getStringExtra("email")
        getHAddress = intent.getStringExtra("hAddress")
        getPassword = intent.getStringExtra("password")
        getAge = intent.getStringExtra("age")
        getGender = intent.getStringExtra("gender")

        val checkUser: Query = FirebaseDatabase.getInstance().getReference("Drivers")
        checkUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (studentDS in snapshot.children) {

                    //progressBar!!.visibility = View.VISIBLE
                    var obj = studentDS.getValue(DriverDBHelperClass::class.java)
                    phone_arrayList.add(obj!!.phone_No)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Sign_up_3rdClass, error.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            }
        })
    }


    fun callNextScreen(view: View) {

        if (!validatePhoneNumber()) {
            return
        }
        var phone: String = ph_number!!.editText!!.getText().toString().trim()
        if (phone.get(0) == '0') {
            phone = phone.substring(1)
        }
        val getPhone: String = "+92" + phone

        val intent = Intent(this@Sign_up_3rdClass, Verify_OTP::class.java)

//        val getfName = intent.getStringExtra("fname")
//        val getEmail = intent.getStringExtra("email")
//        val getHAddress = intent.getStringExtra("hAddress")
//        val getIAddress = intent.getStringExtra("iAddress")
//        val getPassword = intent.getStringExtra("password")
//        val getAge = intent.getStringExtra("age")
//        val getGender = intent.getStringExtra("gender")


        intent.putExtra("fname", getfName)
        intent.putExtra("email", getEmail)
        intent.putExtra("hAddress", getHAddress)
        intent.putExtra("password", getPassword)
        intent.putExtra("age", getAge)
        intent.putExtra("gender", getGender)
        intent.putExtra("phone", getPhone)

//        print(getGender+" "+getfName)

        startActivity(intent)

    }

    private fun validatePhoneNumber(): Boolean {
        var `val`: String = "+92" + ph_number!!.getEditText()!!.getText().toString().trim()


        for (phone in phone_arrayList) {
            if (`val`.equals(phone)) {
                _id++
                break
            }
        }

        val pattern: Pattern
        val matcher: Matcher
        val Phone_PATTERN = "^[+]?[0-9]{10,13}\$"
        pattern = Pattern.compile(Phone_PATTERN)
        matcher = pattern.matcher(`val`)

        return if (`val`.isEmpty()) {
            ph_number!!.setError("Enter valid phone number!")
            false
        } else if (!matcher.matches()) {
            ph_number!!.setError("Invalid Phone Number!")
            return false
        } else if (_id > 0) {
            ph_number!!.setError("Phone Number already Exist!")
            return false
        } else if (`val`.length > 14) {
            ph_number!!.setError("Invalid Phone No!")
            false
        } else {
            ph_number!!.setError(null)
            ph_number!!.setErrorEnabled(false)
            true
        }
    }

    fun callBack(view: View) {
        val intent = Intent(applicationContext, SignUp2ndClass::class.java)
        startActivity(intent)
    }
}