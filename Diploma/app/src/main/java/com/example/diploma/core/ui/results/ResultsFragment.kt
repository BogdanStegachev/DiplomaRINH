package com.example.diploma.core.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.adapters.TestResultAdapter
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.retrofitClasses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResultsFragment : Fragment() {

    private lateinit var recycle:RecyclerView
    private lateinit var tv:TextView
    private lateinit var spinner: Spinner
    private lateinit var tv2:TextView
    private lateinit var spinner2: Spinner
    private var results:ArrayList<ResultsTeacher>? = null

    private var groups:ArrayList<Group>? = null

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
        tv2 = root.findViewById(R.id.textView2)
        spinner2 = root.findViewById(R.id.selectStudSpinner)
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
        spinner.visibility = View.VISIBLE
        tv2.visibility = View.VISIBLE

        val callGroups = api.getGroups("Bearer " + AccountManager.Token)

        callGroups.enqueue(object : Callback<ArrayList<Group>>{
            override fun onFailure(call: Call<ArrayList<Group>>, t: Throwable) {
                //Toast.makeText(requireContext(),)
            }

            override fun onResponse(
                call: Call<ArrayList<Group>>,
                response: Response<ArrayList<Group>>
            ) {
                if(response.code() == 404)
                {
                    Toast.makeText(requireContext(),"Результаты не найдены!", Toast.LENGTH_SHORT).show()
                    return
                }
                if(response.code() != 200)
                {
                    Toast.makeText(requireContext(),"Ошибка!", Toast.LENGTH_SHORT).show()
                    return
                }
                setOnItemSelectedGroup()
                val groupsNames:ArrayList<String> = ArrayList()

                groups = response.body()
                groups!!.forEach {
                    groupsNames.add(it.name)
                }

                val spinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    groupsNames
                )

                spinner.adapter = spinnerAdapter

            }
        })

    }

    private fun setOnItemSelectedGroup()
    {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                val call = api.getResult("Bearer " + AccountManager.Token, groups!![spinner.selectedItemPosition].groupId)

                call.enqueue(object : Callback<ArrayList<ResultsTeacher>>{
                    override fun onFailure(call: Call<ArrayList<ResultsTeacher>>, t: Throwable) {
                        Toast.makeText(requireContext(),"Ошибка!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ArrayList<ResultsTeacher>>,
                        response: Response<ArrayList<ResultsTeacher>>
                    ) {
                        if(response.code() == 404)
                        {
                            Toast.makeText(requireContext(),"Результаты не найдены!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        if(response.code() != 200)
                        {
                            Toast.makeText(requireContext(),"Ошибка!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        results = response.body()!!
                        val studs:ArrayList<String> = ArrayList()
                        setOnItemSelectedStud()
                        response.body()!!.forEach {
                            studs.add(it.name)
                        }

                        val spinnerAdapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            studs
                        )

                        spinner2.adapter = spinnerAdapter

                    }
                })


            }
        }
    }

    private fun setOnItemSelectedStud()
    {
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val adapter = TestResultAdapter()
                if(results!![spinner2.selectedItemPosition].results.size == 0)
                    Toast.makeText(requireContext(),"Студент не прошел ни одного теста!", Toast.LENGTH_SHORT).show()
                else
                    adapter.setItems(results!![spinner2.selectedItemPosition].results)
                recycle.adapter = adapter
            }
        }
    }

    fun enableForStudent()
    {
        tv.visibility = View.GONE
        recycle.visibility = View.VISIBLE
        spinner.visibility = View.GONE
        spinner2.visibility = View.GONE
        tv2.visibility = View.GONE

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