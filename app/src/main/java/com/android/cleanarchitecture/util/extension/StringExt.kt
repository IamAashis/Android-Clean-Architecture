package com.android.cleanarchitecture.util.extension

import android.util.Patterns

/**
 * Created by Aashis on 05,December,2023
 */

fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}