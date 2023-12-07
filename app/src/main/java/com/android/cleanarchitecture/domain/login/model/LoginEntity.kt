package com.android.cleanarchitecture.domain.login.model

/**
 * Created by Aashis on 04,December,2023
 */
data class LoginEntity(
    var id: Int,
    var name: String,
    var email: String,
    var token: String
)