package com.fastrise.app.utill

import android.content.Context
import android.widget.Toast
import com.fastrise.app.utill.AppConstant


fun Context.toast(message:String) =
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()

fun Context.toastShort(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun chkNull(pData: Any?): Any {
    return (pData ?: "")
}



