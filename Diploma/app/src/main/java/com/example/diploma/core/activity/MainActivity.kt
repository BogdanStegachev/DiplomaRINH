package com.example.diploma.core.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.diploma.R
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.retrofitClasses.ProfileResponse
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView:NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        AccountManager.context = applicationContext
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_test, R.id.nav_results, R.id.nav_create,
                R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initUser()
        initLeftMenu()
    }

    override fun onResume() {
        super.onResume()
        initUser()
        initLeftMenu()
        findNavController(R.id.nav_host_fragment).navigate(R.id.nav_test)
    }

    private fun initUser()
    {
        val userJson = String(FileInputStream(File(applicationContext.filesDir, "User.dat")).readBytes())
        try {
            AccountManager.User = Json.parse(ProfileResponse.serializer(), userJson)
        }
        catch (ex:Exception)
        {
            logout()
        }
    }

    private fun initLeftMenu()
    {
        val btn = navView.getHeaderView(0).findViewById<ImageButton>(R.id.logout_btn)
        btn.setOnClickListener { logout() }

        val username = navView.getHeaderView(0).findViewById<TextView>(R.id.username)
        username.text =AccountManager.User!!.name
        val avatar = navView.getHeaderView(0).findViewById<ImageView>(R.id.avatar)
        Picasso.get().load("https://api.rsue.online/avatars/" + AccountManager.User!!.avatar).into(avatar)
    }

    private fun logout()
    {
        val User = File(applicationContext.filesDir, "User.dat")
        User.delete()
        backActivity(this@MainActivity)
    }

    fun backActivity(activity: Activity) {
        val loginActivity = Intent(activity, LoginActivity::class.java)
        activity.startActivity(loginActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
