package com.example.diploma.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.classes.AnswersLogger

class QuizAdapter : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {


    private var answList = ArrayList<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var cardView : CardView = itemView.findViewById(R.id.card_view)
        var itemQuiz: TextView = itemView.findViewById(R.id.answer_text)

        fun bind(ans: String) {
            itemQuiz.text = ans
        }

    }

    fun setItems(answers: ArrayList<String>) {
        clearItems()
        answList = answers
        notifyDataSetChanged()
    }

    fun clearItems() {
        answList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quiz, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return answList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answList[position])
    }
}