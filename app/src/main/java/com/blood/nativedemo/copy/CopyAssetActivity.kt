package com.blood.nativedemo.copy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blood.nativedemo.databinding.ActivityCopyAssetBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CopyAssetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCopyAssetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCopyAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btcCopy1.setOnClickListener {
            GlobalScope.launch {
                CopyUtil.copyAssetsFile2Phone(this@CopyAssetActivity, "log4")
            }
        }

        binding.btcCopy2.setOnClickListener {
            GlobalScope.launch {
                CopyUtil.copyAssetsFile2Phone(this@CopyAssetActivity, "log5")
            }
        }

        binding.btcCopy3.setOnClickListener {
            GlobalScope.launch {
                CopyUtil.copyAssetsFile2Phone(this@CopyAssetActivity, "log6")
            }
        }
    }

}