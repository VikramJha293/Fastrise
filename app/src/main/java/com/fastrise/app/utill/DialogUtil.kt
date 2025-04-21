package com.fastrise.app.utill

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object DialogUtil {

    internal var currentDialog: AlertDialog? = null
    internal var m_cProgressBar: ProgressDialog? = null
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    val MY_PERMISSIONS_REQUEST_CAMERA = 124

    fun hideKeyboard(view: View?, ctx: Context?) {
        if (view != null && ctx != null) {
            val imm = (ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)!!
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @JvmOverloads
    fun displayProgress(pContext: Activity?, message: String = "Please wait..") {

        if (null == m_cProgressBar) {
            if (pContext == null) return
            if (pContext.isFinishing)
                return
            m_cProgressBar = ProgressDialog(pContext)
            m_cProgressBar!!.setCancelable(false)
            m_cProgressBar!!.setMessage(message)
            m_cProgressBar!!.show()

        } else {
            m_cProgressBar!!.setMessage(message)
        }
    }
    @JvmOverloads
    fun displayProgressWithMessage(pContext: Activity?, message: String) {

        if (null == m_cProgressBar) {
            if (pContext == null) return
            if (pContext.isFinishing)
                return
            m_cProgressBar = ProgressDialog(pContext)
            m_cProgressBar!!.setCancelable(false)
            m_cProgressBar!!.setMessage(message)
            m_cProgressBar!!.show()

        } else {
            m_cProgressBar!!.setMessage(message)
        }
    }

    fun stopProgressDisplay() {
        if (null != m_cProgressBar) {
            try {
                m_cProgressBar!!.dismiss()
            } catch (ignored: Exception) {

            }

        }
        m_cProgressBar = null
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkPermission(context: Context): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("External storage permission is necessary")
                    alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which -> ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) }
                    val alert = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    fun checkPermissionPhone(context: Context): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA)) {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("Phone permission is necessary")
                    alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which -> ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CALL_PHONE), MY_PERMISSIONS_REQUEST_CAMERA) }
                    val alert = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CALL_PHONE), MY_PERMISSIONS_REQUEST_CAMERA)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    fun getEndDate1(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }
    fun getEndDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.format("yyyy-MM-dd", cal).toString()
    }
    fun dateToTimestamp(currentDate: String): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(currentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            //throw new IllegalAccessException("Error in parsing date");
        }

        return date!!.time
    }
}

