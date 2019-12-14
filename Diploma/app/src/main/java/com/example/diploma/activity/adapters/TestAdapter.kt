package com.example.diploma.activity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.activity.Test
import com.example.diploma.activity.ui.tests.TestsFragment
import kotlinx.android.synthetic.main.nav_header_main.view.*

class  TestAdapter(private val tests : List<Test>) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    class ViewHolder(testView: View) : RecyclerView.ViewHolder(testView) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewloader, parent, false)
        )
    }
    override fun getItemCount() = tests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val test = tests[position]
        holder.itemView.textView.text = test.Name
    }





}