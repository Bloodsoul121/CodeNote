package com.blood.nativedemo.gps

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blood.nativedemo.databinding.ActivityGpsBinding

class GpsActivity : AppCompatActivity(), LocationHelper.Callback {

    companion object {
        const val TAG = "GpsActivity"
    }

    private lateinit var binding: ActivityGpsBinding
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGpsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationHelper.releaseLocation()
    }

    private fun init() {
        locationHelper = LocationHelper.getInstance()
        locationHelper.addCallback(this)

        binding.gps.setOnClickListener {
            locationHelper.requestLocationUpdate()
            locationHelper.location ?: return@setOnClickListener
            notifyLocation()
        }
    }

    override fun onLocationUpdate(location: Location?) {
        locationHelper.location ?: return
        notifyLocation()
    }

    private fun notifyLocation() {
        binding.location.text = "${locationHelper.location.longitude}\n${locationHelper.location.latitude}\n${locationHelper.location.altitude}"
    }

    override fun onSpeedUpdate(speed: Int) {
        binding.speed.text = "$speed"
    }

}