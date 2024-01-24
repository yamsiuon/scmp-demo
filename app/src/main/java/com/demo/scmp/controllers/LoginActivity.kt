package com.demo.scmp.controllers

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.scmp.R
import com.demo.scmp.databinding.ActivityLoginBinding
import com.demo.scmp.utils.StringUtils
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

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

            }
        }
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
        }
        return true
    }

    private fun showMsg(
        msg: String
    ) {
        Toast.makeText(
            this, msg,
            Toast.LENGTH_SHORT
        ).show()
    }
    
}