package com.blood.nativedemo.property;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.PhoneUtils;
import com.blood.nativedemo.R;
import com.blood.nativedemo.databinding.ActivityPropertyBinding;

import java.util.HashMap;

public class PropertyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PropertyActivity";

    private ActivityPropertyBinding mBinding;

    private HashMap<Integer, String> mMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPropertyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init() {
        mBinding.setClickListener(this);
    }

    private void showInfo(String info) {
        mBinding.info.setText(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imei:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                String imei = PhoneUtils.getIMEI();
                Log.i(TAG, "imei: " + imei);
                showInfo(imei);
                break;
        }
    }
}