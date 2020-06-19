package com.example.diploma.core.ui.tests

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.adapters.TestAdapter
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.classes.OnItemClickListener
import com.example.diploma.core.classes.RecyclerItemClickListener
import com.example.diploma.core.retrofitClasses.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TestsFragment : Fragment() {

    private var adapter = TestAdapter()
    private var tests: ArrayList<Test>? = null
    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var group: Spinner
    private var groups: ArrayList<Group>? = null

    val retrofit = RetrofitInstance.Instance
    val api: ApiRSUE = retrofit.create(ApiRSUE::class.java)

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tests, container, false)

        rv = root.findViewById(R.id.recyclerView)
        fab = root.findViewById(R.id.fab)
        group = root.findViewById(R.id.spGroups)
        initListeners()
        if (AccountManager.User!!.isTeacher) {
            fab.visibility = View.VISIBLE
            group.visibility = View.VISIBLE
            initTeacher()
        } else {
            group.visibility = View.GONE
            fab.visibility = View.GONE
            initStudent()
        }
        rv.layoutManager = GridLayoutManager(activity, 2)
        rv.adapter = adapter


        return root
    }

    private fun initTeacher() {
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

            }
        })
    }

    private fun initStudent() {
        val call = api.getTest("Bearer " + AccountManager.Token)

        call.enqueue(object : Callback<ArrayList<Test>> {
            override fun onFailure(call: Call<ArrayList<Test>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Проверьте ваше подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<ArrayList<Test>>,
                response: Response<ArrayList<Test>>
            ) {
                if (response.code() != 200) {
                    if (response.code() != 404)
                        Toast.makeText(
                            requireContext(),
                            "Ошибка! ${response.code()} ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            requireContext(),
                            "Тесты для Вас не найдены!",
                            Toast.LENGTH_SHORT
                        ).show()
                    return
                }

                val callComp = api.getCompleted("Bearer " + AccountManager.Token)

                val respTests = response.body()!!

                callComp.enqueue(object : Callback<ArrayList<Int>>{
                    override fun onFailure(call: Call<ArrayList<Int>>, t: Throwable) {
                        Toast.makeText(requireContext(), "Что-то пошло не так! Проверьте ваше подключение к сети!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ArrayList<Int>>,
                        response: Response<ArrayList<Int>>
                    ) {
                        if(response.code() != 200)
                        {
//                            Toast.makeText(
//                                requireContext(),
//                                "Ошибка! ${response.code()} ${response.message()}",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            adapter.clearTests()
                            adapter.setTests(respTests)
                            tests = ArrayList()
                            tests!!.addAll(respTests)
                            return
                        }
                        adapter.clearTests()
                        tests = ArrayList()
                        respTests.forEach {
                            if(!response.body()!!.contains(it.testId))
                            {
                                tests!!.add(it)
                                adapter.addTest(it)
                            }
                        }

                    }
                })
            }

        })
    }

    private fun onMenuItemClick(item: MenuItem?, position: Int):Boolean
    {
        when (item.toString()) {
            "Изменить" -> {
                val bundle = Bundle()
                val taskString = tests!![position].toJson()
                bundle.putString("test", taskString)
                findNavController().navigate(R.id.nav_create, bundle)
            }
            "Удалить" -> {
                val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Вы уверены, что хотите удалить тест?")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { _, _ ->
                        removeTest(position)
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .create()
                dialog.setOnShowListener {
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(requireContext().resources.getColor(R.color.colorPrimary))
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(requireContext().resources.getColor(R.color.colorPrimary))
                    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).text = "Удалить"
                }
                dialog.show()


            }
        }

        return true
    }

    private fun removeTest(position: Int)
    {
        val call = api.deleteTest("Bearer " + AccountManager.Token, tests!![position].testId)

        call.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка! Проверьте подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.code() != 204)
                {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка! ${response.code()} ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                adapter.removeAt(position)

            }
        })
    }

    private fun initListeners() {

        rv.addOnItemTouchListener(
            RecyclerItemClickListener(context, rv, object : OnItemClickListener,
                RecyclerItemClickListener.OnItemClickListener {

                override fun onItemClicked(position: Int, view: View) {
                }

                override fun onItemClick(view: View?, position: Int) {
                    if(AccountManager.User!!.isTeacher)
                        return
                    val dialog = AlertDialog.Builder(context)
                        .setTitle("Вы уверены, что хотите пройти тест?")
                        .setPositiveButton(
                            android.R.string.yes
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putString("test", tests!![position].toJson())
                            view!!.findNavController().navigate(R.id.postTestsFragment, bundle)
                        }
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .create()
                    dialog.setOnShowListener {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(requireContext().resources.getColor(R.color.colorPrimary))
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(requireContext().resources.getColor(R.color.colorPrimary))
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).text = "Пройти"
                    }
                    dialog.show()
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    if(!AccountManager.User!!.isTeacher)
                        return

                    val popupMenu = PopupMenu(view!!.context, view)
                    popupMenu.inflate(R.menu.popup_menu)
                    popupMenu.setOnMenuItemClickListener {
                        onMenuItemClick(it, position)
                    }
                    popupMenu.show()

                }
            })
        )
        fab.setOnClickListener {
            findNavController().navigate(R.id.nav_create)
        }

        group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(AccountManager.User!!.isTeacher)
                {
                    val groupId = groups!![group.selectedItemPosition].groupId
                    val call = api.getTestForTeacher("Bearer " + AccountManager.Token, groups!![group.selectedItemPosition].groupId )

                    call.enqueue(object : Callback<ArrayList<Test>>
                    {
                        override fun onFailure(call: Call<ArrayList<Test>>, t: Throwable) {
                            Toast.makeText(
                                requireContext(),
                                "Ошибка! Проверьте подключение к сети",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<ArrayList<Test>>,
                            response: Response<ArrayList<Test>>
                        ) {
                            if (response.code() != 200) {
                                if (response.code() != 404)
                                    Toast.makeText(
                                        requireContext(),
                                        "Ошибка! ${response.code()} ${response.message()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else
                                    Toast.makeText(
                                        requireContext(),
                                        "Тесты для Вас не найдены!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                return
                            }
                            tests = response.body()
                            adapter.setTests(tests!!)
                        }
                    })
                }
            }
        }
    }

}