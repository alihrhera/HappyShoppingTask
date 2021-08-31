package com.taskapp.happyshoppingapp.data.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class UserCallResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("token") val token: String,

    )