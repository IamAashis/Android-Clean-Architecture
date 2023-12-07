package com.android.cleanarchitecture.domain.base

/**
 * Created by Aashis on 07,December,2023
 */
data class ErrorResponse(
    val errors: List<Error>? = null,
)

data class Error(
    var title: String? = null,
    var detail: String? = null,
    var source: ArrayList<String>? = null,
)
