package com.example.diploma.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.diploma.R
import com.example.diploma.core.retrofitClasses.ApiWeather
import com.example.diploma.core.retrofitClasses.Post
import kotlinx.android.synthetic.main.post_tests_fragment.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class PostTestsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.post_tests_fragment, container, false)
        retrofit()
        return root
    }


    fun retrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi: ApiWeather = retrofit.create(ApiWeather::class.java)
        val call = jsonPlaceHolderApi.getPosts()
        call.enqueue(object : Callback<ArrayList<Post>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                if (!response.isSuccessful) {
                    textViewPost.text = "Code: " + response.code()
                    return
                }
                val posts: ArrayList<Post>? = response.body()
                for (post in posts!!) {
                    var content = ""
                    content += "ID: " + post.userId + "\n"
                    content += "User ID: " + post.id + "\n"
                    content += "Title: " + post.title + "\n"
                    content += "Text: " + post.body + "\n\n"
                    textViewPost.append(content)
                }
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                textViewPost.text = t.message
            }
        })
    }
}
