package com.example.diploma.core.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.diploma.R
import com.example.diploma.core.classes.AccountManager
import com.example.diploma.core.retrofitClasses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class LoginActivity : AppCompatActivity() {

    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var but: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login = findViewById(R.id.tvLog)
        password = findViewById(R.id.tvPass)
        AccountManager.context = applicationContext
        but = findViewById(R.id.loginBut)

        but.setOnClickListener { doLogin() }
    }

    private fun doLogin() {
        val retrofit = RetrofitInstance.Retrofit.Instance
        val api = retrofit.create(ApiRSUE::class.java)

        val call = api.login(LoginRequest(login.text.toString(), password.text.toString()))

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(applicationContext, "Ошибка! ${response.code()} ${response.message()}", Toast.LENGTH_SHORT).show()
                    return
                }
                AccountManager.Token = response.body()!!.accessToken
                val callProfile = api.getProfile("Bearer " + AccountManager.Token)

                callProfile.enqueue(object : Callback<ProfileResponse> {
                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
                    ) {
                        if (response.code() != 200) {
                            Toast.makeText(
                                applicationContext,
                                "Ошибка! ${response.code()} ${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                        var isTeacher = false

                        val User = File(applicationContext.filesDir, "User.dat")
                        val fos = FileOutputStream(User)
                        var profileResponse = response.body()!!
                        if(profileResponse.group == null)
                        {
                            isTeacher = true
                            profileResponse.group = Group(0, "")
                        }
                        profileResponse.isTeacher = isTeacher
                        fos.write(profileResponse.toJson().toByteArray())
                        fos.close()

                        finish()
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Ошибка! Проверьте подключение к сети",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                )
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Ошибка! Проверьте подключение к сети",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}
