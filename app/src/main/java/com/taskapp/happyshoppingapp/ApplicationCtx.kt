package com.taskapp.happyshoppingapp

import android.app.Application
import com.taskapp.happyshoppingapp.data.di.DaggerRetrofitComponent
import com.taskapp.happyshoppingapp.data.di.RetrofitComponent
import com.taskapp.happyshoppingapp.data.di.RetrofitModule

class ApplicationCtx : Application() {

    private lateinit var retrofitComponent: RetrofitComponent
    override fun onCreate() {
        super.onCreate()
        retrofitComponent = DaggerRetrofitComponent.builder()
            .retrofitModule(RetrofitModule())
            .build()

    }

    fun getRetrofitComponent(): RetrofitComponent {

        return retrofitComponent
    }

}