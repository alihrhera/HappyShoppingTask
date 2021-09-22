package com.taskapp.happyshoppingapp.data.di

import com.taskapp.happyshoppingapp.data.models.call_response.ProductCallResponse
import com.taskapp.happyshoppingapp.data.models.call_response.UserCallResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiServiceInterface {


    @Headers("Accept: application/json")
    @GET("products")
    fun getAllItems(): Call<ProductCallResponse>;


    @FormUrlEncoded
    @Headers(
        "Accept: application/json",
        "Content-Type: application/x-www-form-urlencoded"
    )


    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserCallResponse>;


    @DELETE("products/{id}")
    @Headers("Accept: application/json")
    fun deleteItem(
        @Header("Authorization") token: String,
        @Path("id") groupId: Long
    ): Call<ProductCallResponse>

}