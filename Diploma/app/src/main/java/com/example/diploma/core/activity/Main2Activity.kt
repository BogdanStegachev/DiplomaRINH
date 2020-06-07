package com.example.diploma.core.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Switch
import com.example.diploma.R
import com.example.diploma.core.classes.SharedPref

class Main2Activity : AppCompatActivity() {

    private  var switch : Switch? = null
    internal lateinit var sharedPref : SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        sharedPref = SharedPref(this)
        if (sharedPref.loadNightModeState() == true)
        {
            setTheme(R.style.DarkTheme)
        }
        else
        {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        switch = findViewById(R.id.enableDark)
        if (sharedPref.loadNightModeState() == true)
        {
            switch!!.isChecked = true
        }

        switch!!.setOnCheckedChangeListener {buttonView, isChecked ->
            if (isChecked) {
                sharedPref.setNightModeState(true)
                restartApp()
            } else {
                sharedPref.setNightModeState(false)
                restartApp()
            }
        }
    }
    fun restartApp(){
        val i = Intent(applicationContext, Main2Activity::class.java)
        startActivity(i)
        finish()
    }
}
