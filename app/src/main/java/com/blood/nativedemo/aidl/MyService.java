package com.blood.nativedemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        Log.i("MyService", "onCreate: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MyService", "onBind: ");
        return new Binder();
    }
}
