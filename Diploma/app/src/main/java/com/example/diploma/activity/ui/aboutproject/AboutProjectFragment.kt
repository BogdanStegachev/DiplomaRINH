package com.example.diploma.activity.ui.aboutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diploma.R


class AboutProjectFragment : Fragment() {

    private lateinit var aboutProjectViewModel: AboutProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutProjectViewModel =
            ViewModelProviders.of(this).get(AboutProjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        aboutProjectViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}