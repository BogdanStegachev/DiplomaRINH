package com.example.diploma.activity.ui.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будут показаны результат тестов"
    }
    val text: LiveData<String> = _text
}