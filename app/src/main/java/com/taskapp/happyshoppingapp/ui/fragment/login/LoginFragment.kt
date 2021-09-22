package com.taskapp.happyshoppingapp.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taskapp.happyshoppingapp.data.enums.CredentialStatus
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.enums.LoginStatus
import com.taskapp.happyshoppingapp.databinding.FragmentLoginBinding
import com.taskapp.happyshoppingapp.ui.MainActivity
import com.taskapp.happyshoppingapp.ui.fragment.home.HomeFragment

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val viewModel = (activity as MainActivity).loginModel

        viewModel.emailValidateLiveData().observe(viewLifecycleOwner,
            {
                if (it == CredentialStatus.INVALID) {
                    binding.emailLayout.error = getString(R.string.invalidEmail)
                } else if (it == CredentialStatus.WRONG) {
                    binding.emailLayout.error = getString(R.string.wrongEmail)

                } else if (it == CredentialStatus.VALID) {
                    binding.emailLayout.error = null
                }
            })


        viewModel.passwordValidateLiveData().observe(viewLifecycleOwner,
            {
                when (it) {
                    CredentialStatus.INVALID -> {
                        binding.passwordLayOut.error = getString(R.string.invalidPassword)
                    }
                    CredentialStatus.WRONG -> {
                        binding.passwordLayOut.error = getString(R.string.wrongPassword)
                    }
                    CredentialStatus.VALID -> {
                        binding.passwordLayOut.error = null
                    }
                }
            })


        binding.singIn.setOnClickListener {
            viewModel.login(
                binding.emailLayout.editText!!.text.toString(),
                binding.passwordLayOut.editText!!.text.toString()
            )
        }



        viewModel.loginStatusLiveData().observe(viewLifecycleOwner, {
            if (it == LoginStatus.SUCCESS) {
                (activity as MainActivity).attachFragment(HomeFragment(), true)
            }
        })



        return binding.root
    }


}