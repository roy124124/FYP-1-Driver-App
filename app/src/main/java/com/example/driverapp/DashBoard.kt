package com.example.driverapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.HashMap

class DashBoard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var nametextView: TextView? = null
    var phonetextView: TextView? = null
    var mainNametextView: TextView? = null
    var drawerLayout: DrawerLayout? = null
    var navView: NavigationView? = null
    var headerView: View? = null
    var toolBar: androidx.appcompat.widget.Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        setContentView(R.layout.activity_dash_board)

        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_View)
        toolBar = findViewById(R.id.toolbar)
        mainNametextView = findViewById(R.id.textView)
        headerView = navView!!.getHeaderView(0)
        nametextView = headerView!!.findViewById(R.id.nametv)
        phonetextView = headerView!!.findViewById(R.id.phonetv)

        val sessionManager =
            DriverSessionManager(this@DashBoard, DriverSessionManager.SESSION_USERSESSION)
        val userDetails: HashMap<String, String> = sessionManager.getUserDetailFromSession()
        val fullName = userDetails[DriverSessionManager.KEY_NAME]
        val Phone_No = userDetails[DriverSessionManager.KEY_PHONE_NO]

        setSupportActionBar(toolBar)

        navView!!.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.navogation_drawer_open,
            R.string.navogation_drawer_closed
        )
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        navView!!.setCheckedItem(R.id.nav_home)
        nametextView!!.setText(fullName)
        phonetextView!!.setText("Driver")
        mainNametextView!!.setText(fullName)
        navView!!.setNavigationItemSelectedListener(this)

    }


    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            moveTaskToBack(true)
            // super.onBackPressed()
        }
    }

    fun callComplaint(view: View) {
        val intent = Intent(applicationContext, Complaints::class.java)
        startActivity(intent)
    }

    fun callAttendance(view: View) {
        val intent = Intent(applicationContext, Attendance::class.java)
        startActivity(intent)
    }

    fun callShifts(view: View) {
        val intent = Intent(applicationContext, Shifts::class.java)
        startActivity(intent)
    }

    fun callLogs(view: View) {
        val intent = Intent(applicationContext, Logs::class.java)
        startActivity(intent)
    }

    fun callSalary(view: View) {
        val intent = Intent(applicationContext, Salary::class.java)
        startActivity(intent)
    }

    fun callViewStudents(view: View) {
        val intent = Intent(applicationContext, ViewStudents::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> Toast.makeText(this, "Home Button", Toast.LENGTH_SHORT).show()
            R.id.nav_notif -> Toast.makeText(this, "Notification Button", Toast.LENGTH_SHORT).show()
            R.id.nav_setting -> Toast.makeText(this, "Setting Button", Toast.LENGTH_SHORT).show()
            R.id.nav_rateus -> Toast.makeText(this, "Rate Us Button", Toast.LENGTH_SHORT).show()
            R.id.nav_rateStudents -> Toast.makeText(this, "Rate Student Button", Toast.LENGTH_SHORT)
                .show()
            R.id.nav_help -> Toast.makeText(this, "Help Button", Toast.LENGTH_SHORT).show()
            R.id.nav_logout -> {
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                val sessionManager =
                    DriverSessionManager(this@DashBoard, DriverSessionManager.SESSION_USERSESSION)
                sessionManager.logoutUserFromSession()
                val sessionManager2 =
                    DriverSessionManager(
                        this@DashBoard,
                        DriverSessionManager.SESSION_REMEMBERME
                    )
                sessionManager2.logoutUserFromSession()
                val intent = Intent(applicationContext, MakeUserSelection::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }
}