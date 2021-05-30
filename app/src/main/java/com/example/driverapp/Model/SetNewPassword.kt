package com.example.driverapp.Model

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.example.driverapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SetNewPassword : AppCompatActivity() {

    var Phone_No: String? = ""
    var new_password: TextInputLayout? = null
    var conf_password: TextInputLayout? = null
    var progressBar: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_new_password)
        Phone_No = intent.getStringExtra("phone") ?: ""
        new_password = findViewById((R.id.new_password))
        conf_password = findViewById((R.id.confirm_password))
        progressBar = findViewById((R.id.np_progress_bar))
    }

    fun setNewPasswordBtn(view: View) {
        if (!validateNewPassword() || !checkSame()) {
            return
        }

        val new_pass = new_password!!.getEditText()!!.getText().toString().trim()


        //Update in the Database

        var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Drivers")
        database.child(Phone_No!!).child("password").setValue(new_pass)


//        val intent = Intent(applicationContext, ForgetPasswordSuccessMessage::class.java)

        startActivity(intent)
        finish()

    }


    fun callBack(view: View) {
        val intent = Intent(applicationContext, Login::class.java)

        startActivity(intent)
        finish()
    }


    private fun validateNewPassword(): Boolean {
        val `val` = new_password!!.getEditText()!!.getText().toString().trim()
        val conf_pass = conf_password!!.getEditText()!!.getText().toString().trim()


        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(`val`)

        // return matcher.matches()
        if (`val`.isEmpty()) {
            new_password!!.setError("Field can not be empty")
            return false
        } else if (!matcher.matches()) {
            new_password!!.setError("Invalid Password Format!")
            return false
        } else if (`val`.length < 9) {
            new_password!!.setError("Password should contain atleast 8 characters!")
            return false
        } else {
            new_password!!.setError(null)
            new_password!!.setErrorEnabled(false)
            return true
        }
    }

    private fun checkSame(): Boolean {
        val `val` = conf_password!!.getEditText()!!.getText().toString().trim()
        val new_pass = new_password!!.getEditText()!!.getText().toString().trim()

        if (!`val`.equals(new_pass)) {
            conf_password!!.setError("Password Must be Same!!!")
            return false
        } else {
            conf_password!!.setError(null)
            conf_password!!.setErrorEnabled(false)
            return true
        }
    }
}