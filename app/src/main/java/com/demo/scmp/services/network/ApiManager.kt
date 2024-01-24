package com.demo.scmp.services.network

import android.util.Log
import com.demo.scmp.BuildConfig
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiManager {

    private val client: OkHttpClient? = null
    private const val CONNECT_TIMEOUT: Long = 5L
    private const val READ_TIMEOUT: Long = 15L

    fun call(httpMethod: HttpMethod, url: String, requestBody: RequestBody?, apiCallBack: ApiCallBack) {

        val request = Request.Builder()
            .url(BuildConfig.API_BASE_URL + url)
            .method(httpMethod.method, requestBody)
            .build()

        val builder = OkHttpClient.Builder()

        builder
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        builder.build().newCall(request).enqueue(object : Callback {
           override fun onFailure(call: Call, e: IOException) {
               apiCallBack.onFail(e.message ?: "error")
           }

           override fun onResponse(call: Call, response: Response) {
               Log.d("onResponse isSuccessful", ":" + response.isSuccessful)
               if(response.isSuccessful) {
                   var res = JSONObject()
                   try {
                       res = JSONObject(response.body?.string())
                       Log.d("onResponse", res.toString())
                   } catch (e: Exception) {

                   }
                   apiCallBack.onSuccess(res)
               } else {
                   apiCallBack.onFail("Call fail")
               }
           }
       })
    }
}