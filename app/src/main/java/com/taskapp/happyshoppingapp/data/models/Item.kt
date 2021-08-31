package com.taskapp.happyshoppingapp.data.models

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Float,
    var like: Boolean
)