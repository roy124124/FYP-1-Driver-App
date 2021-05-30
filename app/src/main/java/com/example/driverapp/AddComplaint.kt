package com.example.driverapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.*

class AddComplaint : AppCompatActivity() {

    var c_title: TextInputLayout? = null
    var c_desc: TextInputLayout? = null
    var phone_No: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_complaint)

        //Hooks
        c_title = findViewById((R.id.text_complaint_title))
        c_desc = findViewById((R.id.complaint_description))


        val sessionManager =
            DriverSessionManager(this@AddComplaint, DriverSessionManager.SESSION_USERSESSION)
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun submitComplaints(view: View) {
        if (!validateTitle() || !validateDescrition()) {
            return
        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)


        var date: String = LocalDate.of(year!!, month!!, day!!).toString()
        val getTitle: String = c_title!!.editText!!.getText().toString().trim()
        val getDesc: String = c_desc!!.editText!!.getText().toString().trim()
        val status: String = "Pending"
        val userClass: String = "Driver"
        val key: String = userClass +" "+ phone_No

        try {
            val ref = FirebaseDatabase.getInstance().getReference("Complaints")
            val addNewUser = ComplaintHelperClass(
                getTitle,
                getDesc,
                date,
                status,
                phone_No,
                userClass
            )

            ref.child(key!!).push().setValue(addNewUser)


        } catch (e: Exception) {
            println(e.message)
        }
        Toast.makeText(
            this@AddComplaint,
            "Complaint submitted successfully!",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(applicationContext, Complaints::class.java)
        startActivity(intent)
        finish()


    }


    fun callBack(view: View) {
        val intent = Intent(applicationContext, Complaints::class.java)
        startActivity(intent)
        finish()
    }

    //Validate Functions
    private fun validateDescrition(): Boolean {
        val `val` = c_desc!!.editText!!.text.toString().trim { it <= ' ' }
        return if (`val`.isEmpty()) {
            c_desc!!.error = "Field can not be empty!"
            false
        } else if (`val`.length < 15) {
            c_desc!!.setError("Description is too Short!");
            return false
        } else {
            c_desc!!.error = null
            c_desc!!.isErrorEnabled = false
            true
        }
    }

    private fun validateTitle(): Boolean {
        val `val` = c_title!!.editText!!.text.toString().trim { it <= ' ' }
        return if (`val`.isEmpty()) {
            c_title!!.error = "Field can not be empty!"
            false
        } else if (`val`.length < 3) {
            c_title!!.setError("Title is too Short!");
            return false
        } else {
            c_title!!.error = null
            c_title!!.isErrorEnabled = false
            true
        }
    }
}