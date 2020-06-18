package com.example.diploma.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.retrofitClasses.ResultResponse

class TestResultAdapter : RecyclerView.Adapter<TestResultAdapter.ViewHolder>() {

    private var resultsList = ArrayList<ResultResponse>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var testNum: TextView = itemView.findViewById(R.id.testNum)
        var errorsCount: TextView = itemView.findViewById(R.id.countErrors)

        fun bind(res: ResultResponse) {
            testNum.text = res.test.name + res.test.theme + ". " + "Оценка: " + res.mark
            errorsCount.text = "Правильность теста: " + res.percent + " процента"
        }
    }

    fun setItems(results: ArrayList<ResultResponse>) {
        clearItems()
        resultsList.addAll(results)
        notifyDataSetChanged()
    }

    fun clearItems() {
        resultsList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_result, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resultsList[position])
    }
}