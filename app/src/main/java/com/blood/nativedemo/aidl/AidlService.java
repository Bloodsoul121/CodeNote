package com.blood.nativedemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.blood.nativedemo.util.LogUtil;

public class AidlService extends Service {

    private final IBinder mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int doTask(int taskId) throws RemoteException {
            LogUtil.Companion.log("AidlService doTask " + taskId);
            return 0;
        }
    };

    @Override
    public void onCreate() {
        Log.i("TAG", "onCreate: ");
        LogUtil.Companion.log("AidlService onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.Companion.log("AidlService onBind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.Companion.log("AidlService onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.Companion.log("AidlService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.Companion.log("AidlService onDestroy");
        super.onDestroy();
    }
}
