package com.example.driverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import java.util.HashMap

class Login : AppCompatActivity() {
    var phone_No: TextInputLayout? = null
    var password: TextInputLayout? = null
    var progressBar: RelativeLayout? = null
    var phone_No_EditText: TextInputEditText? = null
    var password_EditText: TextInputEditText? = null
    var rememberMe: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        phone_No = findViewById((R.id.login_phone_number))
        password = findViewById((R.id.login_password))
        progressBar = findViewById((R.id.login_progress_bar))
        rememberMe = findViewById((R.id.rememberMe))
        phone_No_EditText = findViewById((R.id.login_phone_No_editTExt))
        password_EditText = findViewById((R.id.login_password_editTExt))

        val sessionManager =
            DriverSessionManager(this@Login, DriverSessionManager.SESSION_REMEMBERME)
        if (sessionManager.checkRememberMe()) {
            val rememberMeDetails: HashMap<String, String> =
                sessionManager.getRememberMeDetailFromSession()
            phone_No_EditText!!.setText(rememberMeDetails.get(DriverSessionManager.KEY_SESSIONPHONE_NO))
            password_EditText!!.setText(rememberMeDetails.get(DriverSessionManager.KEY_SESSIONPASSWORD))
        }
    }

    fun callForgetPassword(view: View) {
        val intent = Intent(applicationContext, ForgetPassword::class.java)
        startActivity(intent)
    }

    fun letTheUserLoggedIn(view: View?) {
//        if (!isConnected(this)) {
//            showCustomDialog()
//        }

        if (!validateFields()) {
            return
        }
        progressBar!!.visibility = View.VISIBLE

        var _id: Int = 0;
        var _phone = phone_No!!.editText!!.text.toString().trim { it <= ' ' }
        var _phone2 = phone_No!!.editText!!.text.toString().trim { it <= ' ' }
        if (_phone.get(0) == '0') {
            _phone = _phone.substring(1)
        }
        _phone = "+92" + _phone

        var _password = password!!.editText!!.text.toString().trim { it <= ' ' }


        if (rememberMe!!.isChecked) {
            val sessionManager =
                DriverSessionManager(this@Login, DriverSessionManager.SESSION_REMEMBERME)
            sessionManager.createRememberMeSession(
                _phone2,
                _password
            )
        }
        //DatabaseQuery
        val checkUser: Query =
            FirebaseDatabase.getInstance().getReference("Drivers").orderByChild("phone_No")
                .equalTo(_phone)
        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    phone_No!!.error = null
                    phone_No!!.isErrorEnabled
                    var systemPassword = snapshot.child(_phone).child("password").getValue(
                        String::class.java
                    )

                    if (_password.equals(systemPassword)) {
                        progressBar!!.visibility = View.VISIBLE
                        password!!.isErrorEnabled
                        password!!.error = null
                        var name =
                            snapshot.child(_phone).child("fullName").getValue(String::class.java)
                        var email =
                            snapshot.child(_phone).child("email").getValue(String::class.java)
                        var hAddress =
                            snapshot.child(_phone).child("haddress").getValue(String::class.java)
                        var age = snapshot.child(_phone).child("age").getValue(String::class.java)
                        var gender =
                            snapshot.child(_phone).child("gender").getValue(String::class.java)
                        var phone_number =
                            snapshot.child(_phone).child("phone_No").getValue(String::class.java)
                        var password =
                            snapshot.child(_phone).child("password").getValue(String::class.java)
                        //create a Session
                        val sessionManager =
                            DriverSessionManager(
                                this@Login,
                                DriverSessionManager.SESSION_USERSESSION
                            )
                        sessionManager.createLoginSession(
                            name,
                            email,
                            hAddress,
                            age,
                            gender,
                            phone_number,
                            password
                        )
                        Toast.makeText(this@Login, "Welocome $name", Toast.LENGTH_LONG).show();
                        val intent = Intent(applicationContext, DashBoard::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        progressBar!!.visibility = View.GONE
                        Toast.makeText(this@Login, "Wrong Password", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressBar!!.visibility = View.GONE
                    Toast.makeText(this@Login, "Wrong Credentials", Toast.LENGTH_SHORT).show();

                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar!!.visibility = View.GONE
                Toast.makeText(this@Login, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
    }

    fun callSignUpFromLogin(view: View) {
        val intent = Intent(applicationContext, Signup::class.java)

        startActivity(intent)
    }

    private fun validateFields(): Boolean {
        val _phone = phone_No!!.editText!!.text.toString().trim { it <= ' ' }
        val _password = password!!.editText!!.text.toString().trim { it <= ' ' }
        return if (_phone.isEmpty()) {
            phone_No!!.error = "Phone No can not be empty!"
            phone_No!!.requestFocus()
            false
        } else if (_password.isEmpty()) {
            password!!.error = "Password can not be empty!"
            password!!.requestFocus()
            false
        } else {
            phone_No!!.error = null
            phone_No!!.isErrorEnabled = false
            password!!.error = null
            password!!.isErrorEnabled = false
            true
        }
    }

    fun callBack(view: View) {
        val intent = Intent(applicationContext, MakeUserSelection::class.java)
        startActivity(intent)
    }
}