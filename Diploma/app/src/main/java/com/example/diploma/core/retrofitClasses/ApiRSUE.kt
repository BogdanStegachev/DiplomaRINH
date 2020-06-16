package com.example.diploma.core.retrofitClasses

import retrofit2.Call
import retrofit2.http.GET


interface ApiRSUE {
    @GET("question")
    fun getQuestion():Call<ArrayList<Question>>

    @GET("test")
    fun getTest():Call<ArrayList<Test>>
}

