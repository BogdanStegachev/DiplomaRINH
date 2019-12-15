package com.example.diploma.activity.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.activity.Test
import com.example.diploma.activity.adapters.TestAdapter


class TestsFragment : Fragment() {

    var adapter = TestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tests, container, false)

        var rv : RecyclerView = root.findViewById(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        var testData = Test("Test")
        var testData2 = Test("Test2")
        var list = ArrayList<Test>()
        list.add(testData)
        list.add(testData2)
        adapter.setTest(list)
        return root
    }



}