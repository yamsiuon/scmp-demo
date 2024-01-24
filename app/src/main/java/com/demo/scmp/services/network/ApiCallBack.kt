package com.demo.scmp.services.network

import org.json.JSONObject

interface ApiCallBack {
    fun onFail(e: String)
    fun onError(e: String)
    fun onSuccess(responseData: JSONObject)
}