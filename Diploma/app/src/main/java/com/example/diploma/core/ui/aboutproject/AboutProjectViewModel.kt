package com.example.diploma.core.ui.aboutproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutProjectViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Это приложение для теста студентов находящееся в разработке"
    }
    val text: LiveData<String> = _text
}