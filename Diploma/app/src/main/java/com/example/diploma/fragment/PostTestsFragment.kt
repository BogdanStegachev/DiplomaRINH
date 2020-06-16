package com.example.diploma.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.R
import com.example.diploma.core.retrofitClasses.ApiRSUE
import com.example.diploma.core.retrofitClasses.Question
import kotlinx.android.synthetic.main.post_tests_fragment.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class PostTestsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.post_tests_fragment, container, false)
        retrofit()
        return root
    }


    fun retrofit(){
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
                if (!response.isSuccessful) {
                    textViewPost.text = "Code: " + response.code()
                    return
                }
                val questions: ArrayList<Question>? = response.body()
                for (question in questions!!) {
                    var content = ""
                    content += "ID: " + question.id + "\n"
                    content += "Question: " + question.question + "\n"
                    content += "Answer: " + question.answersList + "\n"
                    content += "CorrectAnswer " + question.correctAnswer + "\n\n"
                    content += "Category" + question.questionCategory + "\n\n"
                    textViewPost.append(content)
                }
            }

            override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                textViewPost.text = t.message
            }
        })
    }
}
