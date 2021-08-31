package com.taskapp.happyshoppingapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.databinding.ActivityMainBinding
import com.taskapp.happyshoppingapp.ui.fragment.home.HomeViewModel
import com.taskapp.happyshoppingapp.ui.fragment.login.LoginFragment
import com.taskapp.happyshoppingapp.ui.fragment.login.LoginViewModel
import com.taskapp.happyshoppingapp.util.AppDialogs
import com.taskapp.happyshoppingapp.util.AppStatus

class MainActivity : AppCompatActivity() {
    val loginModel: LoginViewModel by viewModels()
    val homeViewModel: HomeViewModel by viewModels()
    private var _bind: ActivityMainBinding? = null
    private val binding get() = _bind!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppStatus.Instance.init(this)
        AppDialogs.Instance.init(this)

        _bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachFragment(LoginFragment(), true)

    }

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun attachFragment(fragment: Fragment, replace: Boolean) {
        binding.customToolBar.visibility = View.VISIBLE
        if (fragment is LoginFragment) {
            binding.customToolBar.visibility = View.GONE
        }
        try {
            if (replace) {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
                    .commit()
                return
            }
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment)
                .addToBackStack(null).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

}