package com.example.diploma.core.ui.aboutproject

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.diploma.R
import com.example.diploma.core.activity.Main2Activity
import com.example.diploma.core.activity.MainActivity
import com.example.diploma.core.classes.SharedPref


class AboutProjectFragment : Fragment() {


    private var switch: Switch? = null
    internal lateinit var sharedPref: SharedPref
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        sharedPref = SharedPref(this.context!!)
        if (sharedPref.loadNightModeState() == true) {
            activity!!.setTheme(R.style.DarkTheme)
        } else {
            activity!!.setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        activity!!.setContentView(R.layout.fragment_about)

        switch = activity!!.findViewById(R.id.enableDark)
        if (sharedPref.loadNightModeState() == true) {
            switch!!.isChecked = true
        }

        switch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sharedPref.setNightModeState(true)
                restartApp()
            } else {
                sharedPref.setNightModeState(false)
                restartApp()
            }

        }
        return root

    }

    fun restartApp() {
        val i = Intent(activity, MainActivity::class.java)
        startActivity(i)
        activity!!.finish()

    }
}