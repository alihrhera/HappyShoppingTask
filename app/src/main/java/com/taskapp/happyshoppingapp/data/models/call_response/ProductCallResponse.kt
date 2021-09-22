package com.taskapp.happyshoppingapp.data.models.call_response

import com.google.gson.annotations.SerializedName
import com.taskapp.happyshoppingapp.data.models.app.ProductItem

data class ProductCallResponse (
    @SerializedName("data") val data: List<ProductItem>,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
)