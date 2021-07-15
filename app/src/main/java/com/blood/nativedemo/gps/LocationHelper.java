package com.blood.nativedemo.gps;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.blood.nativedemo.MainApp;

import java.util.Iterator;
import java.util.List;

public class LocationHelper {

    private static final String TAG = "LocationHelper";
    private static final int MIN_TIME = 1000;
    private static final int MIN_DISTANCE = 1;

    private final Application mContext;
    private static LocationHelper mLocationHelper;

    private int mSatelliteCount = 0;
    private LocationManager mLocationManager;
    private Callback mCallback;

    private LocationHelper() {
        mContext = MainApp.Companion.instance();
    }

    private final LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mCallback.onLocationUpdate(location);

            int speed = (int) (location.getSpeed() * 3.6);// m/s ---> km/h
            Log.i(TAG, "onLocationChanged: speed >> " + location.getSpeed() + "m/s >> " + speed + "km/h");

            if (mSatelliteCount >= 3) {
                //当有3颗15强度的卫星时，才显示速度
                mCallback.onSpeedUpdate(speed);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged: ");
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d(TAG, "gps status: available");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d(TAG, "gps status: out of service");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d(TAG, "gps status: temporarily unavailable");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "GPS onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "GPS onProviderDisabled");
        }
    };

    private final GpsStatus.Listener statusListener = new GpsStatus.Listener() {

        public void onGpsStatusChanged(int event) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            switch (event) {
                case GpsStatus.GPS_EVENT_FIRST_FIX: // 第一次定位
                    Log.i(TAG, "GPS_EVENT_FIRST_FIX");
                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS: // 卫星状态改变
                    GpsStatus status = mLocationManager.getGpsStatus(null); // 获取当前状态
                    int maxSatellites = status.getMaxSatellites(); // 获取卫星颗数的默认最大值
                    Iterator<GpsSatellite> it = status.getSatellites().iterator();
                    int count = 0;
                    while (it.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = it.next();
                        if (s.getSnr() >= 15) {
                            count++;
                        }
                    }

                    mSatelliteCount = count;

                    if (mSatelliteCount == 0) {
                        Log.i(TAG, "updateGpsStatus: speed >> 0km/h");
                        mCallback.onSpeedUpdate(0);
                    }
                    break;
                case GpsStatus.GPS_EVENT_STARTED: // 定位启动
                    Log.i(TAG, "GPS_EVENT_STARTED");
                    break;

                case GpsStatus.GPS_EVENT_STOPPED: // 定位结束
                    Log.i(TAG, "GPS_EVENT_STOPPED");
                    break;
            }
        }
    };

    public static LocationHelper getInstance() {
        if (mLocationHelper == null) {
            mLocationHelper = new LocationHelper();
        }
        return mLocationHelper;
    }

    public boolean requestLocationUpdate() {
        LocationManager locationManager = getLocationManager();
        if (locationManager == null) {
            return false;
        }
//        String bestProvider = locationManager.getBestProvider(getCriteria(), true);
//        String provider = checkEnableProvider();
        String provider = LocationManager.GPS_PROVIDER;
        if (TextUtils.isEmpty(provider)) {
            return false;
        }
        try {
            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
            locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
            locationManager.addGpsStatusListener(statusListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (!locationManager.isProviderEnabled(provider)) {
            Log.e(TAG, "requestLocationUpdate: " + provider + " disable");
            return false;
        }
        return true;
    }

    private String checkEnableProvider() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        String provider = "";
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        Log.d(TAG, "checkEnableProvider: " + provider);
        return provider;
    }

    private LocationManager getLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }
        return mLocationManager;
    }

    public Location getLocation() {
        LocationManager locationManager = getLocationManager();
        if (locationManager == null) {
            return null;
        }
        String bestProvider = locationManager.getBestProvider(getCriteria(), true);
        Location loc = null;
        try {
            loc = locationManager.getLastKnownLocation(bestProvider);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return loc;
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(true);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }

    public void releaseLocation() {
        if (mLocationManager != null) {
            try {
                mLocationManager.removeUpdates(mLocationListener);
                mLocationManager.removeGpsStatusListener(statusListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        mLocationManager = null;
        Log.d(TAG, "releaseLocation: success");
    }

    interface Callback {

        void onLocationUpdate(Location location);

        void onSpeedUpdate(int speed);
    }

    public void addCallback(Callback callback) {
        mCallback = callback;
    }
}
