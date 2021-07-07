package com.blood.nativedemo.fragment_pop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blood.nativedemo.R
import com.blood.nativedemo.databinding.ActivityFragmentPushPopBinding

class FragmentPushPopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFragmentPushPopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FragmentA())
        transaction.commit()
    }

}