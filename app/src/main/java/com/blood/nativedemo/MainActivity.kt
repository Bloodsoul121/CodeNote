package com.blood.nativedemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.nativedemo.aidl.JavaServiceActivity
import com.blood.nativedemo.aidl.ServiceActivity
import com.blood.nativedemo.databinding.ActivityMainBinding
import com.blood.nativedemo.fragment_pop.FragmentPushPopActivity
import com.blood.nativedemo.jni.JniActivity
import com.blood.nativedemo.property.PropertyActivity
import com.blood.nativedemo.qrcode.QrcodeActivity
import com.blood.nativedemo.sqlite.SqliteActivity
import com.blood.nativedemo.test.TestActivity
import com.blood.nativedemo.webview.WebviewActivity
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions

class MainActivity : AppCompatActivity(), BindingCallback<MainBean> {

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }

        const val TAG = "MainActivity"
    }

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
        requestPermissions()
    }

    private fun requestPermissions() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
                .requestEach(Manifest.permission.READ_PHONE_STATE)
                .subscribe { permission: Permission ->  // will emit 2 Permission objects
                    if (permission.granted) {
                        Log.i(TAG, "granted: ${permission.name}")
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        Log.i(TAG, "shouldShowRequestPermissionRationale: ${permission.name}")
                    } else {
                        Log.i(TAG, "Denied permission with ask never again: ${permission.name}")
                    }
                }
    }

    private fun init() {
        val adapter = MainAdapter(this)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.setHasFixedSize(true)

        val list = mutableListOf<MainBean>()
        list.add(MainBean("fragment push pop", FragmentPushPopActivity::class.java))
        list.add(MainBean("webview", WebviewActivity::class.java))
        list.add(MainBean("ServiceActivity", ServiceActivity::class.java))
        list.add(MainBean("JavaServiceActivity", JavaServiceActivity::class.java))
        list.add(MainBean("SqliteActivity", SqliteActivity::class.java))
        list.add(MainBean("JniActivity", JniActivity::class.java))
        list.add(MainBean("QrcodeActivity", QrcodeActivity::class.java))
        list.add(MainBean("PropertyActivity", PropertyActivity::class.java))
        list.add(MainBean("LifecycleActivity", LifecycleActivity::class.java))
        list.add(MainBean("TestActivity", TestActivity::class.java))
        adapter.update(list)
    }

    override fun onItemClick(t: MainBean) {
        startActivity(Intent(this, t.clazz))
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String?

}