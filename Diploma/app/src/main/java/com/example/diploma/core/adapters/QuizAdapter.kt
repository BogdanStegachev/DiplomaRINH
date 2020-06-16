package com.example.diploma.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.classes.OnItemClickListener
import com.example.diploma.core.retrofitClasses.Question

class QuizAdapter : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {


    private var QuestionList = ArrayList<Question>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var cardView : CardView = itemView.findViewById(R.id.card_view)
        private var itemQuiz: TextView = itemView.findViewById(R.id.answer_text)
        private  var itemClickListener : OnItemClickListener? = null

        init {

           cardView.setOnClickListener(this)
        }

       override fun onClick(view : View) {

        }

        fun bind(subject: Subject, isTeacher: Boolean) {
            time.text = subject.startTime + " - " + subject.endTime
            name.text = subject.title
            if (isTeacher)
                teacher.text = subject.group.name
            else
                teacher.text = subject.teacher.position + " " + subject.teacher.name
            room.text = subject.classroom.number + " " + subject.classroom.corps
            type.text = subject.typeOfWork
            this.address = subject.classroom.address
        }

    }


    fun setItems(subjects: ArrayList<Subject>) {
        clearItems()
        if (subjects.size != 0)
            SubjectList.addAll(subjects)
        else {
            val emptyTitle:String = if(isTeacher)
                "В этот день у преподавателя нет занятий"
            else
                "В этот день у группы нет занятий"

            val lesson = Subject(
                0,
                emptyTitle,
                "",
                "",
                Classroom("", "", ""),
                Teacher(-1, "", ""),
                Group(-1, -1, ""),
                0,
                ""
            )
            val subj = ArrayList<Subject>()
            subj.add(lesson)
            SubjectList.addAll(subj)
        }
        notifyDataSetChanged()
    }

    fun clearItems() {
        SubjectList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return SubjectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(SubjectList[position], isTeacher)
    }
}