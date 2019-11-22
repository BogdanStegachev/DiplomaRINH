package com.example.diploma.activity.ui.tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Фрагмент для тестов"
    }
    val text: LiveData<String> = _text
}