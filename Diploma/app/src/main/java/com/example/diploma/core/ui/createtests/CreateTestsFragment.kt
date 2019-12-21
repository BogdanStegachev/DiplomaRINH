package com.example.diploma.core.ui.createtests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diploma.R


class CreateTestsFragment : Fragment() {

    private lateinit var createTestsViewModel: CreateTestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createTestsViewModel =
            ViewModelProviders.of(this).get(CreateTestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_results, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        createTestsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}