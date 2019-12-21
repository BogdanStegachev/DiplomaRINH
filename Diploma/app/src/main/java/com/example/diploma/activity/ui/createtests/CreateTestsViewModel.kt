package com.example.diploma.activity.ui.createtests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateTestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Создание студента(еще в разрабоке)"
    }
    val text: LiveData<String> = _text
}