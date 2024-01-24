package com.demo.scmp.utils

import android.util.Patterns
import java.util.regex.Pattern

object StringUtils {

    @JvmStatic
    fun isValidEmail(txt: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(txt).matches()
    }

    @JvmStatic
    fun isValidPassword(txt: String): Boolean {
        return Pattern.compile("^(?=.*[a-zA-Z0-9]).{6,10}+$").matcher(txt).matches()

    }
}