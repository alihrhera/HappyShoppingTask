package com.taskapp.happyshoppingapp.ui.fragment.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.taskapp.happyshoppingapp.data.enums.CredentialStatus
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.taskapp.happyshoppingapp.ApplicationCtx
import com.taskapp.happyshoppingapp.data.di.RetrofitApiServiceInterface
import com.taskapp.happyshoppingapp.data.enums.LoginStatus
import com.taskapp.happyshoppingapp.data.models.call_response.UserCallResponse
import com.taskapp.happyshoppingapp.util.AppStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "LoginViewModel"

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
     lateinit var apiInterFace: RetrofitApiServiceInterface

    private val emailValidate = MutableLiveData<CredentialStatus>()
    private val passwordValidate = MutableLiveData<CredentialStatus>()
    private val loginStatus = MutableLiveData<LoginStatus>()

    init {
       (application as ApplicationCtx).getRetrofitComponent().inject(this)

    }


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

        AppStatus.loading()
        apiInterFace.login(email!!, password!!)
            .enqueue(object :
                Callback<UserCallResponse> {
                override fun onResponse(
                    call: Call<UserCallResponse>,
                    response: Response<UserCallResponse>
                ) {
                    AppStatus.normal()

                    when {
                        response.code() == 200 -> {
                            val body = response.body()
                            loginStatus.postValue(LoginStatus.SUCCESS)
//                            ApiClient.Instance.USER_TOKEN=body!!.token
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
                        else -> {

                        }
                    }

                }

                override fun onFailure(call: Call<UserCallResponse>, t: Throwable) {
                    AppStatus.normal()
                    Log.e(TAG, "onFailure: ${call.request().body()}+ Error :- ${t.message}")

                }
            })
    }


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