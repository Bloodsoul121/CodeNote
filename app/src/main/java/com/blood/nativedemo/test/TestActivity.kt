package com.blood.nativedemo.test

import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blood.nativedemo.databinding.ActivityTestBinding
import com.blood.nativedemo.thread.MyTask
import com.ebanma.customjson.JSON
import java.nio.charset.Charset

class TestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TestActivity"
        const val msg1 = "qqq"
        const val msg2 = "collect"
    }

    private lateinit var binding: ActivityTestBinding

    private var encode1 = "cXFx"
    private var encode2 = "Y29sbGVjdA=="

    private var encryptText = "V0lGSQ==;V0lGSQ==;MTA=;UmVkbWk=;5Lit5ZyL6IGv6YCa;NTUz;MTAwMDAw;c2Rr;MS4wLjA=;MmMwMmU5OTUxYTQ5NDM5ODdhOGQ1NjhjMjM0MGMyOGRk;UmVkbWkgSzMw;dW5rbm93bg==;dW5rbm93bg==;emhfQ04=;ZTA6MWY6ODg6ZWU6NDU6NmQ=;YW5kcm9pZA==;UUtRMS4xOTA4MjUuMDAyIHRlc3Qta2V5cw==;Kzg2MTMyNjc5OTYyNzE=;cGhvbmU=;MTA4MCoyMTc1;R01UKzA4OjAwIEFzaWEvU2hhbmdoYWk=;ZTA6MWY6ODg6ZWU6NDU6NmQ=;MzAuODIuMTQzLjY5;RkU4MDo6NzNGQTo1MUU3OjM4OUE6RjgyNg==;dW5rbm93bg=="
    private val encryptKey = "EzWhZiGfiIril1bTV5cISoktxy3kEIsZ8wi6p3N80UyoLxODuWURqGZMdHTxMozdjTboz3vTlmosaQRuEQb4YzEp7KM9VxAXY81QmoZA6zqrstLeSBmW4MwERdOKpjL1ueqgrvhiBcY2ggw8co3y5AqL7GiBHl82TLctSPSfwMAUBZxQw5ja7JljeMaXArMOldVM2tBf8bz3DhxqCLYfXGsKAdrgbBw1jX513QBGkHHiS33t3myYAUv0STxnIedU"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.base64Encode.setOnClickListener {
            encode1 = Base64.encodeToString(msg1.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            encode2 = Base64.encodeToString(msg2.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            Log.i(TAG, "encode: $encode1")
            Log.i(TAG, "encode: $encode2")
        }
        binding.base64Decode.setOnClickListener {
            val decode1 = String(Base64.decode(encode1, Base64.URL_SAFE), Charset.forName("UTF-8"))
            val decode2 = String(Base64.decode(encode2, Base64.URL_SAFE), Charset.forName("UTF-8"))
            Log.i(TAG, "decode: $decode1 $decode2")

            val device = "V0lGSQ==;V0lGSQ==;MTA=;UmVkbWk=;5Lit5ZyL6IGv6YCa;NTUz;MTAwMDAw;c2Rr;MS4wLjA=;MTIzNDU2Nzg5MDEyMzQ1Njc=;UmVkbWkgSzMw;dW5rbm93bg==;dW5rbm93bg==;emhfQ04=;ZTA6MWY6ODg6ZWU6NDU6NmQ=;YW5kcm9pZA==;UUtRMS4xOTA4MjUuMDAyIHRlc3Qta2V5cw==;Kzg2MTMyNjc5OTYyNzE=;cGhvbmU=;MTA4MCoyMTc1;R01UKzA0OjMwIEFzaWEvS2FidWw=;ZTA6MWY6ODg6ZWU6NDU6NmQ="
            val session = "ZjNmZjI0MmFjNjQxMDVhYg==;Y29tLnl1bm9zLmRyaXZlcmRlbW8=;REQtU0RL5rWL6K-V;MS4w;MQ==;MTg=;MjI=;MTYyNTU2NTU2Nzc1NA==;MTYyNTU2NTU3MDU0MQ==;eHhvbw==;eGlhb21pbmdAMTYzLmNvbQ=="
            val event = "Z29vZHM=;dW5rbm93bg=="
            device.split(";").forEach {
                Log.i(TAG, "device decode: " + String(Base64.decode(it, Base64.URL_SAFE), Charset.forName("UTF-8")))
            }
            session.split(";").forEach {
                Log.i(TAG, "session decode: " + String(Base64.decode(it, Base64.URL_SAFE), Charset.forName("UTF-8")))
            }
            event.split(";").forEach {
                Log.i(TAG, "event decode: " + String(Base64.decode(it, Base64.URL_SAFE), Charset.forName("UTF-8")))
            }
        }
        /**
        Y29sbGVjdA==;cXFx

        2021-07-07 10:39:49.031 I/TestActivity: encode: cXFx
        Y29sbGVjdA==
        2021-07-07 10:39:52.075 I/TestActivity: decode: qqq collect
         */

        binding.customJsonTo.setOnClickListener {
            val customObj = CustomObj()
            val json = JSON.toJSONString(customObj)
            Log.i(TAG, "customJsonTo: $json")
        }
        binding.customJsonFrom.setOnClickListener {
            val json = "{\"age\":18,\"name\":\"blood\"}"
            val customObj = JSON.parseObject(json, CustomObj::class.java)
            Log.i(TAG, "customJsonFrom: $customObj")
        }

        binding.encrypted.setOnClickListener {
            Log.i(TAG, "encryptText before : $encryptText")
            encryptText = RC4Crypt.encrypt(encryptText, encryptKey)
            Log.i(TAG, "encryptText after : $encryptText")
        }

        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.i(TAG, "androidId : $androidId")

        binding.asyncTask.setOnClickListener {
            for (i in 1..10) {
                MyTask().execute(i)
            }
        }
    }

}