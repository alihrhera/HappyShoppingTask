package com.taskapp.happyshoppingapp.data.di

import com.taskapp.happyshoppingapp.ui.fragment.home.HomeViewModel
import com.taskapp.happyshoppingapp.ui.fragment.login.LoginViewModel
import dagger.Component
import javax.inject.Singleton


/*
* Dagger2 Component
*
* */

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {
    fun inject(loginViewModel:LoginViewModel)
    fun inject(homeViewModel: HomeViewModel)
}