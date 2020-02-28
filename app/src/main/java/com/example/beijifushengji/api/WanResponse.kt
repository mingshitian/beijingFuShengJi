package com.example.beijifushengji.api

data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)