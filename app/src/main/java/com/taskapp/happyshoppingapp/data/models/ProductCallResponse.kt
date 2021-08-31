package com.taskapp.happyshoppingapp.data.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class ProductCallResponse (
    @SerializedName("data") val data: List<Item>,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
)