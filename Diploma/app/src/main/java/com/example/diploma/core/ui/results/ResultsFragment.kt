package com.example.diploma.core.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.adapters.TestResultAdapter
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.retrofitClasses.ApiRSUE
import com.example.diploma.core.retrofitClasses.ResultResponse
import com.example.diploma.core.retrofitClasses.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResultsFragment : Fragment() {

    private lateinit var recycle:RecyclerView
    private lateinit var tv:TextView
    private lateinit var spinner: Spinner

    val retrofit = RetrofitInstance.Retrofit.Instance
    val api = retrofit.create(ApiRSUE::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_results, container, false)

        tv = root.findViewById(R.id.textView)
        recycle = root.findViewById(R.id.rResultsStudent)
        spinner = root.findViewById(R.id.selectGroupSpinner)
        recycle.layoutManager = LinearLayoutManager(requireContext())

        if(AccountManager.User!!.isTeacher)
        {
            enableForTeacher()
        }
        else
        {
            enableForStudent()
        }

        return root
    }

    fun enableForTeacher()
    {
        tv.visibility = View.VISIBLE
        recycle.visibility = View.VISIBLE
        spinner.visibility = View.VISIBLE
    }

    fun enableForStudent()
    {
        tv.visibility = View.GONE
        recycle.visibility = View.VISIBLE
        spinner.visibility = View.GONE

        val call = api.getResult("Bearer " + AccountManager.Token)

        call.enqueue(object : Callback<ArrayList<ResultResponse>>{
            override fun onFailure(call: Call<ArrayList<ResultResponse>>, t: Throwable) {
                Toast.makeText(requireContext(),"Что-то пошло не так! Проверьте ваше подключение к сети!", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ArrayList<ResultResponse>>,
                response: Response<ArrayList<ResultResponse>>
            ) {
                if(response.code() != 200)
                {
                    if (response.code() == 404)
                    {
                        Toast.makeText(requireContext(),"Результаты не найдены" , Toast.LENGTH_LONG).show()
                        return
                    }
                    Toast.makeText(requireContext(),"Ошибка! ${response.code()} ${response.message()}", Toast.LENGTH_LONG).show()
                    return
                }

                val adapter = TestResultAdapter()
                recycle.adapter = adapter
                adapter.setItems(response.body()!!)
            }
        })

    }
}