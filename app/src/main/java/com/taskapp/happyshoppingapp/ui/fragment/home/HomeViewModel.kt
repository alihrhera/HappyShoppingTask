package com.taskapp.happyshoppingapp.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taskapp.happyshoppingapp.data.models.Item
import com.taskapp.happyshoppingapp.data.models.ProductCallResponse
import com.taskapp.happyshoppingapp.data.network.ApiClient
import com.taskapp.happyshoppingapp.util.AppStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    /**
     * the productListProvider is MutableLiveData
     * when call post value will update value to observer
     *
     */
    private val productListProvider = MutableLiveData<List<Item>>()

    /**
     * the deleteStatus is MutableLiveData
     * when call post value will update value to observer
     *
     */
    private val deleteStatus = MutableLiveData<Item>()



    fun getItemsLiveData(): LiveData<List<Item>> {
        return productListProvider
    }



    fun deleteLiveData(): LiveData<Item> {
        return deleteStatus
    }


    fun getItems() {
        AppStatus.Instance.loading()
        ApiClient.Instance.getItems().enqueue(object :
            Callback<ProductCallResponse> {
            override fun onResponse(
                call: Call<ProductCallResponse>,
                response: Response<ProductCallResponse>
            ) {
                AppStatus.Instance.normal()


                if (response.code() == 200) {
                    if (response.body()!!.status) {
                        productListProvider.postValue(response.body()!!.data)
                    }

                } else {
                    Log.e(
                        TAG, "getItems onResponse:" +
                                " ${response.code()} ${response.errorBody()} "
                    )
                }
            }

            override fun onFailure(call: Call<ProductCallResponse>, t: Throwable) {
                AppStatus.Instance.normal()
                Log.e(
                    TAG,
                    " getItems onFailure Error :- ${t.message} >> ${t.cause} >> ${t.printStackTrace()} "
                )
            }
        })
    }


    fun deleteItemById(item: Item) {
        AppStatus.Instance.loading()
        ApiClient.Instance.deleteItem(item.id).enqueue(object :
            Callback<ProductCallResponse> {
            override fun onResponse(
                call: Call<ProductCallResponse>,
                response: Response<ProductCallResponse>
            ) {
                AppStatus.Instance.normal()
                if (response.code() == 200) {
                    if (response.body()!!.status) {
                        deleteStatus.postValue(item)
                    }
                }
            }

            override fun onFailure(call: Call<ProductCallResponse>, t: Throwable) {
                AppStatus.Instance.normal()
                Log.e(
                    TAG,
                    "deleteItemById onFailure Error :- ${t.message} >> ${t.cause} >> ${t.printStackTrace()} "
                )
            }
        })
    }


    private val TAG = "HomeViewModel"

}