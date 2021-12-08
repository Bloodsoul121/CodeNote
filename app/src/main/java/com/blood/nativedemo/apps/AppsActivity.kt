package com.blood.nativedemo.apps

import android.R
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ImageUtils
import com.blood.nativedemo.databinding.ActivityAppsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlin.math.max

class AppsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AppsActivity"
        private const val ICON_SIZE_BLUR_FACTOR = 0.5f / 48
        private const val ICON_SIZE_KEY_SHADOW_DELTA_FACTOR = 1f / 48
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGetApps.setOnClickListener {
            Glide.with(this)
//                .load(AppUtils.getAppIcon())
                .load(adaptAppIcon())
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.ivIcon)
        }
    }

    private fun adaptAppIcon(): Bitmap? {
        val icon = AppUtils.getAppIcon() ?: return null
        val appIconSize = resources.getDimensionPixelSize(R.dimen.app_icon_size)
        val resBitmap = icon.toBitmap()
        val resBitmapSize = max(resBitmap.width, resBitmap.height)
        if (resBitmapSize > appIconSize) {
            val factor: Float = 1 / (1 + 2 * ICON_SIZE_BLUR_FACTOR + ICON_SIZE_KEY_SHADOW_DELTA_FACTOR)
            val offset = (resBitmapSize - appIconSize + (resBitmapSize * (1 - factor)).toInt()) / 2
            return ImageUtils.clip(resBitmap, offset, offset, resBitmap.width - 2 * offset, resBitmap.height - 2 * offset)
        }
        return resBitmap
    }

}