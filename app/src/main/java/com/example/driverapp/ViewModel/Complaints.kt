package com.example.driverapp.ViewModel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.button.MaterialButton

class Complaints : AppCompatActivity() {

    var existing_Complaints: MaterialButton? = null
    var new_Complaints: MaterialButton? = null
    var callBack: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_complaints)
    }

    fun viewExistingComplaints(view: View) {
       // val intent = Intent(applicationContext, ViewExistingComplaints::class.java)
        startActivity(intent)
    }

    fun addNewComplaint(view: View) {
        //val intent = Intent(applicationContext, AddComplaint::class.java)
        startActivity(intent)
    }

    fun callBack(view: View) {
        val intent = Intent(applicationContext, DashBoard::class.java)
        startActivity(intent)
        finish()
    }
}