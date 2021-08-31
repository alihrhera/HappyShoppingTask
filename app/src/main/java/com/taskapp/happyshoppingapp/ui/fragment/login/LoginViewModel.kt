package com.taskapp.happyshoppingapp.ui.fragment.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taskapp.happyshoppingapp.data.models.CredentialStatus
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import com.taskapp.happyshoppingapp.data.models.LoginStatus
import com.taskapp.happyshoppingapp.data.models.UserCallResponse
import com.taskapp.happyshoppingapp.data.network.ApiClient
import com.taskapp.happyshoppingapp.util.AppStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private val emailValidate = MutableLiveData<CredentialStatus>()
    private val passwordValidate = MutableLiveData<CredentialStatus>()
    private val loginStatus = MutableLiveData<LoginStatus>()


    fun login(email: String?, password: String?) {
        if (!isValidEmail(email ?: "")) {
            emailValidate.postValue(CredentialStatus.INVALID)
            return
        }
        emailValidate.postValue(CredentialStatus.VALID)

        if (!isValidPassword(password ?: "")) {
            passwordValidate.postValue(CredentialStatus.INVALID)
            return
        }
        passwordValidate.postValue(CredentialStatus.VALID)

        AppStatus.Instance.loading()
        ApiClient.Instance.login(email!!, password!!).enqueue(object :
            Callback<UserCallResponse> {
            override fun onResponse(
                call: Call<UserCallResponse>,
                response: Response<UserCallResponse>
            ) {
                AppStatus.Instance.normal()

                when {
                    response.code() == 200 -> {
                        val body = response.body()
                        loginStatus.postValue(LoginStatus.SUCCESS)
                        ApiClient.Instance.USER_TOKEN=body!!.token
                    }
                    response.code() == 422 -> {
                        Log.e(
                            TAG,
                            "onResponse login : ${response.errorBody().toString()} "
                        )
                        loginStatus.postValue(LoginStatus.FAIL)
                        emailValidate.postValue(CredentialStatus.WRONG)
                    }
                    response.code() == 401 -> {
                        Log.e(
                            TAG,
                            "onResponse login : ${response.errorBody().toString()} "
                        )
                        passwordValidate.postValue(CredentialStatus.WRONG)
                    }
                }

            }

            override fun onFailure(call: Call<UserCallResponse>, t: Throwable) {
                AppStatus.Instance.normal()
                Log.e(TAG, "onFailure: ${call.request().body()}+ Error :- ${t.message}")

            }
        })
    }

    private val TAG = "LoginViewModel"


    fun emailValidateLiveData(): LiveData<CredentialStatus> {
        return emailValidate
    }

    fun passwordValidateLiveData(): LiveData<CredentialStatus> {
        return passwordValidate
    }

    fun loginStatusLiveData(): LiveData<LoginStatus> {
        return loginStatus
    }


    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length > 5
    }


}