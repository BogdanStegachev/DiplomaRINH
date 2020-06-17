package com.example.diploma.core.ui.tests

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.core.classes.Test
import com.example.diploma.core.adapters.TestAdapter
import com.example.diploma.core.classes.OnItemClickListener
import java.io.File


class TestsFragment : Fragment() {

    private var adapter = TestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tests, container, false)

        var rv: RecyclerView = root.findViewById(R.id.recyclerView)
        var internalStorageDir : File = context!!.filesDir
        rv.layoutManager = GridLayoutManager(activity, 2)
        rv.adapter = adapter
        var testData = Test("Test")
        var testData2 = Test("Test2")
        var testData3 = Test("Test3")
        var list = ArrayList<Test>()
        list.add(testData)
        list.add(testData2)
        list.add(testData3)
        rv.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Toast.makeText(activity, "nice", Toast.LENGTH_SHORT).show()
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Вы уверены, что хотите пройти тест?")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { _, _ ->
                        view.findNavController().navigate(R.id.postTestsFragment)
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
        })
        adapter.setTest(list)
        return root


    }


    private fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }
        })
    }

}