package com.demo.scmp.services.network

enum class ApiUrl(val endpoint: String) {
    API_LOGIN(endpoint = "login?delay=5"),
    API_USERS(endpoint = "users")
}