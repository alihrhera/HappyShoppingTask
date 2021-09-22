package com.taskapp.happyshoppingapp.ui.fragment.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taskapp.happyshoppingapp.data.di.RetrofitApiServiceInterface
import com.taskapp.happyshoppingapp.data.models.app.ProductItem
import com.taskapp.happyshoppingapp.data.models.call_response.ProductCallResponse
import com.taskapp.happyshoppingapp.util.AppStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

//private const val TAG = "HomeViewModel"


class HomeViewModel(application: Application) : AndroidViewModel(application) {


    @Inject
    lateinit var apiInterFace: RetrofitApiServiceInterface

    /**
     * the productListProvider is MutableLiveData
     * when call post value will update value to observer
     *
     */
    private val productListProvider = MutableLiveData<Map<String, Any>>()

    /**
     * the deleteStatus is MutableLiveData
     * when call post value will update value to observer
     *
     */

    private val deleteStatus = MutableLiveData<Map<String, Any>>()
//    private val errorStatus = MutableLiveData<Map<String, Any>>()


    fun getItemsLiveData(): LiveData<Map<String, Any>> {
        return productListProvider
    }


    fun deleteLiveData(): LiveData<Map<String, Any>> {
        return deleteStatus
    }


    fun getItems() {
        AppStatus.loading()
        apiInterFace.getAllItems().enqueue(object :
            Callback<ProductCallResponse> {
            override fun onResponse(
                call: Call<ProductCallResponse>,
                response: Response<ProductCallResponse>
            ) {
                AppStatus.normal()


                if (response.code() == 200) {
                    if (response.body()!!.status) {
                        productListProvider.postValue(
                            mapOf(
                                "error" to false,
                                "data" to response.body()!!.data
                            )
                        )
                    }

                } else {
                    productListProvider.postValue(
                        mapOf(
                            "error" to true,
                            "error_message" to " Delete ItemById onResponse  Code:-${response.code()} Error:-${response.errorBody()}"
                        )
                    )

                }
            }

            override fun onFailure(call: Call<ProductCallResponse>, t: Throwable) {
                AppStatus.normal()

                productListProvider.postValue(
                    mapOf(
                        "error" to true,
                        "error_message" to " getItems onFailure Error :- ${t.message} >> ${t.cause} >> ${t.printStackTrace()} "
                    )
                )
            }
        })
    }


    fun deleteItemById(item: ProductItem) {
        AppStatus.loading()
        apiInterFace.deleteItem("", item.id).enqueue(object :
            Callback<ProductCallResponse> {
            override fun onResponse(
                call: Call<ProductCallResponse>,
                response: Response<ProductCallResponse>
            ) {
                AppStatus.normal()
                if (response.code() == 200) {
                    if (response.body()!!.status) {
                        deleteStatus.postValue(mapOf("error" to false, "data" to item))
                    }
                } else {
                    mapOf(
                        "error" to true,
                        "error_message" to " Delete ItemById onResponse  Code:-${response.code()} Error:-${response.errorBody()}"
                    )
                }
            }

            override fun onFailure(call: Call<ProductCallResponse>, t: Throwable) {
                AppStatus.normal()

                productListProvider.postValue(
                    mapOf(
                        "error" to true,
                        "error_message" to " Delete ItemById onFailure Error :- ${t.message} >> ${t.cause} >> ${t.printStackTrace()} "
                    )
                )
            }
        })
    }


}