package com.demo.scmp.controllers.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.demo.scmp.R
import com.demo.scmp.databinding.ActivityLoginBinding
import com.demo.scmp.services.network.ApiCallBack
import com.demo.scmp.services.network.ApiManager
import com.demo.scmp.services.network.ApiUrl
import com.demo.scmp.services.network.HttpMethod
import com.demo.scmp.utils.StringUtils
import okhttp3.FormBody
import org.json.JSONObject


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClick()
    }

    private fun setOnClick() {
        binding.btnLogin.setOnClickListener {
            if (checkLoginField()) {
                callApiLogin()
            }
        }
    }

    private fun callApiLogin() {
        binding.layoutLoading.lltLoading.visibility = View.VISIBLE
        val requestBody = FormBody.Builder()
            .add("email", binding.edEmail.text.toString())
            .add("password", binding.edPassword.text.toString())
            .build()
        ApiManager.call(
            HttpMethod.POST,
            ApiUrl.API_LOGIN.endpoint,
            requestBody,
            object : ApiCallBack {
                override fun onFail(e: String) {
                    runOnUiThread {
                        binding.layoutLoading.lltLoading.visibility = View.GONE
                        showMsg(e)
                        clearData()
                    }

                }

                override fun onSuccess(responseData: JSONObject) {
                    runOnUiThread {
                        binding.layoutLoading.lltLoading.visibility = View.GONE
                        if (responseData.has("token")) {
                            clearData()
                            val i =
                                Intent(this@LoginActivity, StaffListActivity::class.java)
                            i.putExtra("token", responseData.getString("token"))
                            startActivity(i)
                        } else {
                            showMsg("login fail")
                            clearData()
                        }
                    }

                }
            })
    }

    private fun clearData() {
//        binding.edEmail.setText("")
        binding.edPassword.setText("")
    }

    private fun checkLoginField(): Boolean {
        val validEmail = StringUtils.isValidEmail(binding.edEmail.text.toString())
        val validPassword = StringUtils.isValidPassword(binding.edPassword.text.toString())
        val sb = StringBuilder()
        if (!validEmail) {
            sb.append(getString(R.string.email_format_invalid))
        }
        if (!validPassword) {
            if (sb.toString().isNotEmpty()) {
                sb.append("\n")
            }
            sb.append(getString(R.string.password_format_invalid))
        }
        if (!validEmail || !validPassword) {
            showMsg(sb.toString())
            Handler(Looper.myLooper()!!).postDelayed({
                showMsg(getString(R.string.please_try_again))
            }, 1000)

            return false
        }
        return true
    }

}