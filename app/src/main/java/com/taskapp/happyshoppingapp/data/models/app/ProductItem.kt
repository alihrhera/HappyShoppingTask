package com.taskapp.happyshoppingapp.data.models.app

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Float,
    var like: Boolean
)