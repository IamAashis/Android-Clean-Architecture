package com.android.cleanarchitecture.data.login.remote.dto

/**
 * Created by Aashis on 04,December,2023
 */
data class LoginResponse(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var token: String? = null,
)
