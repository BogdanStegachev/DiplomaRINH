package com.example.diploma.core.classes

class AnswersLogger {
    companion object {
        var correctAnswers: Int = 0

        fun checkToCorrect(ans: Int, corAns: Int) {
            if (ans == corAns)
                correctAnswers += 1
        }
    }
}