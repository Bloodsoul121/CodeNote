package com.blood.nativedemo.jni

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blood.nativedemo.R

class JniActivity : AppCompatActivity() {

//    companion object {
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
    }

    fun clickBtn1(view: View) {
        nativeFunc1(123)
    }

    external fun nativeFunc1(arg: Int)

}