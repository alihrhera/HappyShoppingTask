package com.taskapp.happyshoppingapp.data.network

import android.util.Log
import com.taskapp.happyshoppingapp.data.models.ProductCallResponse
import com.taskapp.happyshoppingapp.data.models.UserCallResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://android-training.appssquare.com/api/"

class ApiClient {
    object Instance {
        private lateinit var apiInterFace: ApiInterFace
        var USER_TOKEN = ""


        init {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiInterFace = retrofit.create(ApiInterFace::class.java)

        }


        fun login(email: String, password: String): Call<UserCallResponse> {
            return apiInterFace.login(email, password)
        }


        fun getItems(): Call<ProductCallResponse> {
            return apiInterFace.getAllItems()
        }

        fun deleteItem(itemId: Long): Call<ProductCallResponse> {
            val token = " Bearer $USER_TOKEN"
            return apiInterFace.deleteItem(
                token,
                itemId
            )
        }


    }


}
