package com.example.diploma.core.retrofitClasses


import com.google.gson.annotations.SerializedName

data class Post(
    var userId: Int,
    var id: Int,
    var title: String,
    var body: String
)