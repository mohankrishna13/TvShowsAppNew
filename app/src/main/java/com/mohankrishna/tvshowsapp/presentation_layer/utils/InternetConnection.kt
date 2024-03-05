package com.mohankrishna.tvshowsapp.presentation_layer.utils

import android.content.Context
import android.net.ConnectivityManager

class InternetModeProvider(private val context: Context) {

    val isNetworkConnected: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }
}