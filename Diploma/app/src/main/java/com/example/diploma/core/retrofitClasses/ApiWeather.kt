package com.example.diploma.core.retrofitClasses

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiWeather {
    @GET("posts")
    fun getPosts():Call<ArrayList<Post>>
}

