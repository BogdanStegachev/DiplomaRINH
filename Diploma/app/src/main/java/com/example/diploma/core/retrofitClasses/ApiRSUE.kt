package com.example.diploma.core.retrofitClasses

import retrofit2.Call
import retrofit2.http.*


interface ApiRSUE {
    @GET("question")
    fun getQuestion():Call<ArrayList<Question>>

    @POST("Authorization/Token")
    fun login(@Body body: LoginRequest):Call<LoginResponse>

    @GET("profile")
    @Headers("Content-Type: application/json")
    fun getProfile(@Header("Authorization") Authorization: String):Call<ProfileResponse>

    @GET("tests")
    fun getTest(@Header("Authorization") Authorization: String):Call<ArrayList<Test>>

    @GET("tests")
    fun getTestForTeacher(@Header("Authorization") Authorization: String, @Query("groupId") groupId: Int):Call<ArrayList<Test>>

    @POST("tests")
    fun newTest(@Header("Authorization") Authorization: String, @Body body:Test):Call<Test>

    @GET("teacher/groups")
    fun getGroups(@Header("Authorization") Authorization: String):Call<ArrayList<Group>>

    @PUT("tests/{id}")
    fun updateTest(@Header("Authorization") Authorization: String, @Body body: Test, @Path("id") taskId: Long):Call<Void>

    @DELETE("tests")
    fun deleteTest(@Header("Authorization") Authorization: String, @Query("id") id:Int):Call<Void>

    @POST("results")
    fun sendResult(@Header("Authorization") Authorization: String, @Body body:ResultRequest):Call<ResultResponse>

    @GET("results")
    fun getResult(@Header("Authorization") Authorization: String):Call<ArrayList<ResultResponse>>

    @GET("tests/completed")
    fun getCompleted(@Header("Authorization") Authorization: String):Call<ArrayList<Int>>
}

