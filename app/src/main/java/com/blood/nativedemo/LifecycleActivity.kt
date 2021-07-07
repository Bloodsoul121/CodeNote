package com.blood.nativedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class LifecycleActivity : AppCompatActivity() {
    
    companion object {
        const val TAG = "LifecycleActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
    }

    override fun onRestart() {
        Log.i(TAG, "onRestart: ")
        super.onRestart()
    }

    override fun onStart() {
        Log.i(TAG, "onStart: ")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume: ")
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }
    
}