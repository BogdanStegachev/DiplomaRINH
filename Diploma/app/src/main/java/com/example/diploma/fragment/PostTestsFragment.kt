package com.example.diploma.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.adapters.QuizAdapter
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.classes.AnswersLogger
import com.example.diploma.core.classes.OnItemClickListener
import com.example.diploma.core.classes.RecyclerItemClickListener
import com.example.diploma.core.retrofitClasses.*
import com.example.diploma.core.retrofitClasses.RetrofitInstance.Retrofit.Instance
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PostTestsFragment : Fragment() {
    private var questions: ArrayList<Question>? = null
    private var index: Int = 0
    private var test:Test? = null

    val retrofit = RetrofitInstance.Retrofit.Instance
    val api = retrofit.create(ApiRSUE::class.java)

    private lateinit var QuestionTitle: TextView
    private lateinit var QuestionText: TextView
    private lateinit var Quiz: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.post_tests_fragment, container, false)
        QuestionTitle = root.findViewById(R.id.tvQuestionTitle)
        QuestionText = root.findViewById(R.id.tvQuestionText)
        Quiz = root.findViewById(R.id.rvQuiz)

        Quiz.layoutManager = GridLayoutManager(requireContext(), 1)

        //Quiz.layoutManager = LinearLayoutManager(requireContext())
        //retrofit()
        val bundle = arguments
        if (arguments != null) {
            test = Json.parse(Test.serializer(), bundle?.getString("test")!!)
            questions = test!!.questions
            initQuestions()
        }

        AnswersLogger.correctAnswers = 0

        Quiz.addOnItemTouchListener(
            RecyclerItemClickListener(context, Quiz, object : OnItemClickListener,
                RecyclerItemClickListener.OnItemClickListener {

                override fun onItemClicked(position: Int, view: View) {
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
        val retrofit = RetrofitInstance.Instance
        val jsonPlaceHolderApi: ApiRSUE = retrofit.create(ApiRSUE::class.java)
        val call = jsonPlaceHolderApi.getQuestion()
        call.enqueue(object : Callback<ArrayList<Question>> {
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
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Проверьте ваше подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initQuestions() {
        if(index < questions!!.size)
        {
            QuestionText.text = questions!![index].question
            QuestionTitle.text = "Вопрос " + (index + 1).toString() + " из " + questions!!.size
            val adapter = QuizAdapter()
            adapter.setItems(questions!![index].answers)
            Quiz.adapter = adapter
        }
        else
        {
            val call = api.sendResult("Bearer " + AccountManager.Token, ResultRequest(test!!.testId, AnswersLogger.correctAnswers))

            call.enqueue(object : Callback<ResultResponse>{
                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),"Что-то пошло не так! Проверьте подключение к сети!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    if(response.code()!=200)
                    {
                        Toast.makeText(
                            requireContext(),
                            "Ошибка! ${response.code()} ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    Toast.makeText(requireContext(),"Тест сохранен! Процент правильных ответов - "+response.body()!!.percent+". Оценка за тест: "+ response.body()!!.mark, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }

            })

            AnswersLogger.correctAnswers = 0
        }
    }

}
