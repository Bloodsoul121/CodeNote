package com.blood.nativedemo.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.blood.nativedemo.databinding.ActivityServiceBinding
import com.blood.nativedemo.util.LogUtil

class ServiceActivity : AppCompatActivity() {

    private lateinit var aidlService: IMyAidlInterface

    private lateinit var aidlServiceIntent: Intent

    private val deathRecipient = object : IBinder.DeathRecipient {

        override fun binderDied() {
            LogUtil.log("binderDied")

            aidlService.asBinder().unlinkToDeath(this, 0)
        }

    }

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtil.log("onServiceConnected")

            aidlService = IMyAidlInterface.Stub.asInterface(service)

            aidlService.asBinder().linkToDeath(deathRecipient, 0)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtil.log("onServiceDisconnected")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            LogUtil.log("click")
            bindService(Intent(this, AidlService::class.java), serviceConnection, BIND_AUTO_CREATE)
        }

        binding.btn2.setOnClickListener {
            LogUtil.log("click")
            unbindService(serviceConnection)
        }

        aidlServiceIntent = Intent(this, AidlService::class.java)

        binding.btn3.setOnClickListener {
            LogUtil.log("click")
            startService(aidlServiceIntent)
        }

        binding.btn4.setOnClickListener {
            LogUtil.log("click")
            stopService(aidlServiceIntent)
        }
    }

}