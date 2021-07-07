package com.blood.nativedemo.util

import android.util.Log

class LogUtil {

    companion object {

        const val TAG = "LogUtil";

        fun log(msg: String) {
            Log.i(TAG, "log: $msg")
        }

    }

}