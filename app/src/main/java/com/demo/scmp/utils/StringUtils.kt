package com.demo.scmp.utils

import android.util.Patterns
import java.util.regex.Pattern

object StringUtils {

    @JvmStatic
    fun isValidEmail(txt: String): Boolean {
        return Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(txt).matches()
//        return Patterns.EMAIL_ADDRESS.matcher(txt).matches()
    }

    @JvmStatic
    fun isValidPassword(txt: String): Boolean {
        return Pattern.compile("^(?=.*[a-zA-Z0-9]).{6,10}+$").matcher(txt).matches()

    }
}