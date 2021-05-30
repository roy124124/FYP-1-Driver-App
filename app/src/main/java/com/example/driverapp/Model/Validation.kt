package com.example.driverapp.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.textfield.TextInputLayout

public class Validation(){

    var c_title: TextInputLayout? = null
    var c_desc: TextInputLayout? = null
    var phone_No: String? = null



    //Validate Functions

    public fun validateDescrition(): Boolean {
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