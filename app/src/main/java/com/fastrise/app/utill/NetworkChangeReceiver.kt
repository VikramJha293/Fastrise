package com.fastrise.app.utill

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {
    private var isConnected = false
    override fun onReceive(context: Context, intent: Intent) {
        Log.v(LOG_TAG, "Receieved notification about network status")
        isNetworkAvailable(context)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v(LOG_TAG, "Now you are connected to Internet!")
                            Toast.makeText(context, "Internet availablle via Broadcast receiver", Toast.LENGTH_SHORT).show()
                            isConnected = true

                            // do your processing here ---
                            // if you need to post any data to the server or get
                            // status
                            // update from the server
                        }
                        return true
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!")
        Toast.makeText(context, "Internet NOT availablle via Broadcast receiver", Toast.LENGTH_SHORT).show()
        isConnected = false
        return false
    }

    companion object {
        private const val LOG_TAG = "NetworkChangeReceiver"
    }
}