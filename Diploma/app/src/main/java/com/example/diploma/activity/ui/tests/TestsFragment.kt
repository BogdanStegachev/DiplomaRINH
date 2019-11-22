package com.example.diploma.activity.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diploma.R


class TestsFragment : Fragment() {

    private lateinit var testsViewModel: TestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testsViewModel =
            ViewModelProviders.of(this).get(TestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tests, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        testsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}