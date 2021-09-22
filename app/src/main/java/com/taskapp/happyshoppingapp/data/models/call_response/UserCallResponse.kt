package com.taskapp.happyshoppingapp.data.models.call_response

import com.google.gson.annotations.SerializedName

data class UserCallResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("token") val token: String,

    )