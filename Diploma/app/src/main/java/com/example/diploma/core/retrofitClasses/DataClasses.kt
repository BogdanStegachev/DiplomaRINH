package com.example.diploma.core.retrofitClasses

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.collections.ArrayList

@Serializable
data class Test(
    val testId: Int,
    val groupId: Int,
    val name: String,
    val theme: String,
    val questions: ArrayList<Question>
) {
    fun toJson(): String {
        return Json.stringify(serializer(), this)
    }
}

@Serializable
data class Question(
    var questionId: Int,
    var question: String,
    var answers: ArrayList<String>,
    var correctAnswer: Int
) {
//    fun toJson(): String {
//        return Json.stringify(serializer(), this)
//    }
}

data class LoginResponse(
    val accessToken: String
)

data class LoginRequest(
    val Username: String,
    val Password: String
)

@Serializable
data class ProfileResponse(
    val name: String,
    val avatar: String,
    var group: Group,
    var isTeacher: Boolean
) {
    fun toJson(): String {
        return Json.stringify(serializer(), this)
    }
}

@Serializable
data class Group(
    val groupId: Int,
    val name: String
)


data class ResultRequest(
    val testId:Int,
    val rightAnswers:Int
)

data class ResultResponse(
    val percent:Int,
    val mark:Int,
    val test:Test
)