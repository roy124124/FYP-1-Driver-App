package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class ForgetPassword : AppCompatActivity() {

    var ph_number: TextInputLayout? = null
    var progressBar: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        ph_number = findViewById((R.id.f_ph_number))
        progressBar = findViewById((R.id.fp_progress_bar))

    }

    fun callNextScreen(view: View) {

        if (!validatePhoneNumber()) {
            return
        }

        progressBar!!.visibility = View.VISIBLE



        var _phone = ph_number!!.editText!!.text.toString().trim { it <= ' ' }
        if (_phone.get(0) == '0') {
            _phone = _phone.substring(1)
        }
        _phone = "+92" + _phone

        //DatabaseQuery
        val checkUser: Query =
            FirebaseDatabase.getInstance().getReference("Drivers").orderByChild("phone_No")
                .equalTo(_phone)
        progressBar!!.visibility = View.VISIBLE
        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    ph_number!!.error = null
                    ph_number!!.isErrorEnabled
                    val intent = Intent(applicationContext, FPVerifyOTP::class.java)
                    intent.putExtra("phone", _phone)
                    startActivity(intent)
                    finish()

                    progressBar!!.visibility = View.GONE

                } else {
                    progressBar!!.visibility = View.GONE
                    ph_number!!.error = "No Such User Exist!"
                    ph_number!!.requestFocus()
                    Toast.makeText(
                        this@ForgetPassword,
                        "No Such User Exists!!!",
                        Toast.LENGTH_SHORT
                    ).show();

                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar!!.visibility = View.GONE
                Toast.makeText(this@ForgetPassword, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
    }

    fun callBack(view: View) {
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun validatePhoneNumber(): Boolean {
        val `val`: String = "+92" + ph_number!!.getEditText()!!.getText().toString().trim()


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
        } else if (`val`.length > 14) {
            ph_number!!.setError("Invalid Phone Number!")
            false
        } else {
            ph_number!!.setError(null)
            ph_number!!.setErrorEnabled(false)
            true
        }
    }
}