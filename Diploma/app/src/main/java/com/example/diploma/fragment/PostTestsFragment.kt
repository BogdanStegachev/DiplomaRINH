package com.example.diploma.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.adapters.QuizAdapter
import com.example.diploma.core.classes.AnswersLogger
import com.example.diploma.core.classes.OnItemClickListener
import com.example.diploma.core.classes.RecyclerItemClickListener
import com.example.diploma.core.retrofitClasses.ApiRSUE
import com.example.diploma.core.retrofitClasses.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PostTestsFragment : Fragment() {
    private var questions: ArrayList<Question>? = null

    private var index: Int = 0

    private lateinit var questionTitle: TextView
    private lateinit var questionText: TextView
    private lateinit var quiz: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.post_tests_fragment, container, false)
        questionTitle = root.findViewById(R.id.tvQuestionTitle)
        questionText = root.findViewById(R.id.tvQuestionText)
        quiz = root.findViewById(R.id.rvQuiz)
        quiz.layoutManager = LinearLayoutManager(requireContext())
        retrofit()

        quiz.addOnItemTouchListener(
            RecyclerItemClickListener(context, quiz, object : OnItemClickListener,
                RecyclerItemClickListener.OnItemClickListener {

                override fun onItemClicked(position: Int, view: View) {
                    val i = 0
                }

                override fun onItemClick(view: View?, position: Int) {
                    AnswersLogger.checkToCorrect(position, questions!![index].correctAnswer)
                    index++
                    initQuestions()
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    AnswersLogger.checkToCorrect(position, questions!![index].correctAnswer)
                    index++
                    initQuestions()
                }
            })
        )

        return root
    }


    fun retrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://193.7.217.242:7000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi: ApiRSUE = retrofit.create(ApiRSUE::class.java)
        val call = jsonPlaceHolderApi.getQuestion()
        call.enqueue(object : Callback<ArrayList<Question>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ArrayList<Question>>,
                response: Response<ArrayList<Question>>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                    return
                }
                questions = response.body()
                initQuestions()
            }

            override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                //textViewPost.text = t.message
            }
        })
    }

    private fun initQuestions() {
        questionText.text = questions!![index].question
        questionTitle.text = "Вопрос " + (index + 1).toString() + " из " + questions!!.size
        val adapter = QuizAdapter()
        adapter.setItems(questions!![index].answersList)
        quiz.adapter = adapter
    }

}
