package com.example.diploma.core.retrofitClasses

import java.util.*

data class Test(
    val id : String,
    val testName : String,
    val testTheme : String,
    val creationTime : Date
)
data class Question(
    val id : String,
    val question : String,
    val answersList : ArrayList<String>,
    val correctAnswer : Int,
    val questionCategory : Int
)