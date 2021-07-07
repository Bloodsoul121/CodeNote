package com.blood.nativedemo

import android.app.ActivityManager
import android.app.Application
import android.os.Process
import android.util.Log
import kotlin.properties.Delegates

class MainApp : Application() {

    companion object {

        const val TAG = "MainApp"

        private var instance : MainApp by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Log.i(TAG, "onCreate: ${getCurProcessName()}")

    }

    private fun getCurProcessName(): String? {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        activityManager.runningAppProcesses.forEach {
            if (Process.myPid() == it.pid) {
                return it.processName
            }
        }
        return null
    }

}