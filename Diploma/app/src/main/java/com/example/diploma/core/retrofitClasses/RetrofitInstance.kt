package com.example.diploma.core.retrofitClasses

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object Retrofit
    {
        private var retrofit: retrofit2.Retrofit? = null

        val Instance: retrofit2.Retrofit
            get() {
                if (retrofit == null) {
                    retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl("https://api.rsue.online/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit!!
            }
    }
}