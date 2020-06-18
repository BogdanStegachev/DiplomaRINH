package com.example.diploma.core.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.retrofitClasses.Question
import com.example.diploma.core.retrofitClasses.Test
import com.shawnlin.numberpicker.NumberPicker

class TestCreateAdapter : RecyclerView.Adapter<TestCreateAdapter.ViewHolder>() {

    var questions = ArrayList<Question>()

    inner class ViewHolder(testView: View) : RecyclerView.ViewHolder(testView) {
        var qText: EditText = itemView.findViewById(R.id.questionText)
        var answ1: EditText = testView.findViewById(R.id.answ1)
        var answ2: EditText = testView.findViewById(R.id.answ2)
        var answ3: EditText = testView.findViewById(R.id.answ3)
        var answ4: EditText = testView.findViewById(R.id.answ4)
        var picker: NumberPicker = itemView.findViewById(R.id.npicker)
        var closeBtn:ImageView = itemView.findViewById(R.id.deleteBtn)

        fun initEvents() {
            qText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    questions[adapterPosition].question = qText.text.toString()
                }
            })

            answ1.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    questions[adapterPosition].answers[0] = answ1.text.toString()
                }
            })

            answ2.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    questions[adapterPosition].answers[1] = answ2.text.toString()
                }
            })

            answ3.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    questions[adapterPosition].answers[2] = answ3.text.toString()
                }
            })

            answ4.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    questions[adapterPosition].answers[3] = answ4.text.toString()
                }
            })

            picker.setOnValueChangedListener { picker, oldVal, newVal ->
                questions[adapterPosition].correctAnswer = newVal - 1
            }

            closeBtn.setOnClickListener {
                if(questions.size>1)
                {
                    questions.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }
        fun bind(question: Question) {
            if(question.answers.size!=4)
            {
                for (i in 0..3)
                {
                    questions[adapterPosition].answers.add("")
                }
            }
            initEvents()

            qText.setText(question.question)
            if(question.answers.size == 4)
            {
                answ1.setText(question.answers[0])
                answ2.setText(question.answers[1])
                answ3.setText(question.answers[2])
                answ4.setText(question.answers[3])
            }
            else
            {
                answ1.setText("")
                answ2.setText("")
                answ3.setText("")
                answ4.setText("")
            }
            picker.value = question.correctAnswer + 1

            picker.minValue = 1
            picker.maxValue = 4
        }
    }

    fun setItems(quests: ArrayList<Question>) {
        clearItems()
        this.questions.addAll(quests)
        notifyDataSetChanged()
    }

    fun addItem() {
        questions.add(Question(questions.size, "", ArrayList(), 0))
        notifyDataSetChanged()
    }

    fun clearItems() {
        questions.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.test_create_one, parent, false)
        )
    }

    override fun getItemCount() = questions.size

    fun getQuestion(position: Int): Question {
        return questions[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }


}