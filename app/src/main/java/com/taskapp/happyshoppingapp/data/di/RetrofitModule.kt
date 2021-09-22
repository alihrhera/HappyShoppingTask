package com.taskapp.happyshoppingapp.data.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://android-training.appssquare.com/api/"

@Module
class RetrofitModule {
    
    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun getClintInterFace(retrofit: Retrofit): RetrofitApiServiceInterface {
        return retrofit.create(RetrofitApiServiceInterface::class.java)
    }







}
