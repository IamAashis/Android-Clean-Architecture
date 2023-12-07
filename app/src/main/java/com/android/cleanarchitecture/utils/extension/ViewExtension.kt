package com.android.cleanarchitecture.utils.extension

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

/**
 * Created by Aashis on 05,December,2023
 */

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showAlertDialog(message: String) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}