package com.example.diploma.core.ui.createtests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.adapters.TestCreateAdapter
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.retrofitClasses.ApiRSUE
import com.example.diploma.core.retrofitClasses.Group
import com.example.diploma.core.retrofitClasses.RetrofitInstance
import com.example.diploma.core.retrofitClasses.Test
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateTestsFragment : Fragment() {

    private lateinit var testName: EditText
    private lateinit var testTheme: EditText
    private lateinit var questions: RecyclerView
    private lateinit var addQBut: Button
    private lateinit var addTestBut: Button
    private lateinit var group: Spinner

    private var test: Test? = null

    private var groups: ArrayList<Group>? = null
    private var adapter = TestCreateAdapter()
    val retrofit = RetrofitInstance.Retrofit.Instance
    val api = retrofit.create(ApiRSUE::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create, container, false)

        testName = root.findViewById(R.id.etTestName)
        testTheme = root.findViewById(R.id.etTestTheme)
        questions = root.findViewById(R.id.testQuestions)
        addQBut = root.findViewById(R.id.addQ)
        addTestBut = root.findViewById(R.id.addNewTest)
        group = root.findViewById(R.id.spGroups)
        questions.layoutManager = LinearLayoutManager(context)
        adapter = TestCreateAdapter()
        loadGroups()
        addQBut.setOnClickListener { initNew(false) }

        questions.adapter = adapter
        val bundle = arguments
        if (bundle != null) {
            test = Json.parse(Test.serializer(), bundle.getString("test")!!)
            addTestBut.text = "Изменить тест"
            testName.setText(test!!.name)
            testTheme.setText(test!!.theme)
            adapter.setItems(test!!.questions)
            addTestBut.setOnClickListener { editTest() }


            return root
        }

        addTestBut.setOnClickListener { addNewTest() }

        initNew(true)
        return root
    }

    private fun editTest() {
        if (!checkLastToFull() || testName.text.toString() == "" || testTheme.text.toString() == "") {
            Toast.makeText(
                requireContext(),
                "Все поля должны быть заполнены!",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val groupId = groups!![group.selectedItemPosition].groupId

        test = Test(
            test!!.testId,
            groupId,
            testName.text.toString(),
            testTheme.text.toString(),
            adapter.questions
        )

        val call = api.updateTest("Bearer " + AccountManager.Token, test!!, test!!.testId.toLong())

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Проверьте подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 204) {
                    Toast.makeText(requireContext(), "Тест успешно обновлен!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка! ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun loadGroups() {
        val call = api.getGroups("Bearer " + AccountManager.Token)

        call.enqueue(object : Callback<ArrayList<Group>> {
            override fun onFailure(call: Call<ArrayList<Group>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Проверьте подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }

            override fun onResponse(
                call: Call<ArrayList<Group>>,
                response: Response<ArrayList<Group>>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка! ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                    return
                }

                val groupsNames = ArrayList<String>()

                groups = response.body()

                groups!!.forEach {
                    groupsNames.add(it.name)
                }

                val spinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    groupsNames
                )

                group.adapter = spinnerAdapter

                if (test != null) {
                    var i = 0
                    groups!!.forEach {
                        if (it.groupId == test!!.groupId)
                            group.setSelection(i)
                        i++
                    }
                }

            }
        })
    }

    private fun addNewTest() {
        if (!checkLastToFull() || testName.text.toString() == "" || testTheme.text.toString() == "") {
            Toast.makeText(
                requireContext(),
                "Все поля должны быть заполнены!",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        val groupId = groups!![group.selectedItemPosition].groupId

        val test =
            Test(0, groupId, testName.text.toString(), testTheme.text.toString(), adapter.questions)

        val call = api.newTest(
            "Bearer " + AccountManager.Token,
            test
        )

        call.enqueue(object : Callback<Test> {
            override fun onFailure(call: Call<Test>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Проверьте подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<Test>,
                response: Response<Test>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(requireContext(), "Тест успешно добавлен!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun initNew(isFirst: Boolean) {
        if (isFirst) {
            adapter.addItem()
            return
        }
        if (checkLastToFull())
            adapter.addItem()
        else
            Toast.makeText(requireContext(), "Все поля должны быть заполнены!", Toast.LENGTH_SHORT)
                .show()
    }

    private fun checkLastToFull(): Boolean {
        val lQuestion = adapter.getQuestion(adapter.itemCount - 1)
        if (lQuestion.question == "")
            return false
        lQuestion.answers.forEach {
            if (it == "")
                return false
        }
        for (i in 0..3) {
            if (lQuestion.answers[i] == "")
                return false
        }
        return true
    }
}