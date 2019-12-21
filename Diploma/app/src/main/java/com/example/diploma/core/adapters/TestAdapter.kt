package com.example.diploma.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.core.classes.Test
import com.example.diploma.R


class  TestAdapter : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var testList = ArrayList<Test>()

    class ViewHolder(testView: View) : RecyclerView.ViewHolder(testView) {
        private  var test : TextView = testView.findViewById(R.id.Test)

        fun bind(test1 : Test){
            test.text = test1.Name
        }
    }

        fun setTest(tests : ArrayList<Test>){
            clearTests()
            testList.addAll(tests)
        }

    fun clearTests() {
        testList.clear()
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewloader, parent, false)
        )
    }
    override fun getItemCount() = testList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(testList[position])
    }


}