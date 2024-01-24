package com.demo.scmp

import com.demo.scmp.utils.StringUtils
import org.junit.Assert
import org.junit.Test

class LoginTest {
    @Test
    fun field_checking_true() {
        Assert.assertTrue(StringUtils.isValidEmail("a@a.com"))
        Assert.assertTrue(StringUtils.isValidPassword("abcdefg"))
    }

    @Test
    fun field_checking_false() {
        Assert.assertFalse(StringUtils.isValidEmail("aa.com"))
        Assert.assertFalse(StringUtils.isValidPassword("abcd###..fg"))
        Assert.assertFalse(StringUtils.isValidPassword("abc"))
    }
}