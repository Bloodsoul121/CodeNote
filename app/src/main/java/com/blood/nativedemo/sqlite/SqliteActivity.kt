package com.blood.nativedemo.sqlite

import android.content.ContentProviderOperation
import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blood.nativedemo.databinding.ActivitySqliteBinding
import com.blood.nativedemo.sqlite.SqliteConstants.*
import java.util.*

class SqliteActivity : AppCompatActivity() {

    private val TAG = "SqliteActivity"

    private lateinit var binding: ActivitySqliteBinding

    private val random = Random()

    private var insertUri: Uri? = null

    private var operationUri = URI_BLOOD

    private val handler = Handler(Looper.getMainLooper())

    private val contentObserver = object : ContentObserver(handler) {

        override fun deliverSelfNotifications(): Boolean {
            Log.i(TAG, "deliverSelfNotifications: ")
            return super.deliverSelfNotifications()
        }

        override fun onChange(selfChange: Boolean) {
            Log.i(TAG, "onChange: $selfChange")
            super.onChange(selfChange)
        }

        override fun onChange(selfChange: Boolean, uri: Uri?) {
            Log.i(TAG, "onChange >> : $selfChange $uri")
            super.onChange(selfChange, uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSwitch.setOnClickListener {
            operationUri = if (operationUri.equals(URI_BLOOD)) URI_SOUL else URI_BLOOD
            binding.btnSwitch.text = operationUri.path
            Log.i(TAG, "switch operationUri: $operationUri")
        }

        binding.btnRegisterObserver.setOnClickListener {
            contentResolver.registerContentObserver(operationUri, true, contentObserver)
        }

        binding.btnUnregisterObserver.setOnClickListener {
            contentResolver.unregisterContentObserver(contentObserver)
        }

        binding.btnInsert.setOnClickListener {
            val randomNum = random.nextInt(1000)
            val contentValues = ContentValues()
//            contentValues.put("sn", 4674)
            contentValues.put("sn", SystemClock.uptimeMillis() % 10000)
            contentValues.put("name", "bloodsoul")
//            contentValues.put("version", ++version)
            contentValues.put("content", "content$randomNum")
            contentValues.put("timestamp", System.currentTimeMillis())
            insertUri = contentResolver.insert(operationUri, contentValues)
            Log.i(TAG, "insert insertUri: $insertUri")
        }

        binding.btnInsertNull.setOnClickListener {
            insertUri = contentResolver.insert(operationUri, null)
//            insertUri = contentResolver.insert(URI_BLOOD, ContentValues())
        }

        binding.btnQuery.setOnClickListener {
            val cursor = contentResolver.query(operationUri, null, null, null, null)
            cursor ?: return@setOnClickListener
            while (cursor.moveToNext()) {
                val bean = BloodBean()
                bean.sn = cursor.getInt(cursor.getColumnIndex("sn"))
                bean.name = cursor.getString(cursor.getColumnIndex("name"))
                bean.version = cursor.getInt(cursor.getColumnIndex("version"))
                bean.content = cursor.getString(cursor.getColumnIndex("content"))
                Log.i(TAG, "btnQueryCurrentInsert: $bean")
            }
            cursor.close()
        }

        binding.btnQueryCurrentInsert.setOnClickListener {
            insertUri?.let {
                Log.i(TAG, "parseId: ${ContentUris.parseId(it)}")
                val cursor = contentResolver.query(it, null, null, null, null)
                cursor ?: return@setOnClickListener
                while (cursor.moveToNext()) {
                    val bean = BloodBean()
                    bean.sn = cursor.getInt(cursor.getColumnIndex("sn"))
                    bean.name = cursor.getString(cursor.getColumnIndex("name"))
                    bean.version = cursor.getInt(cursor.getColumnIndex("version"))
                    bean.content = cursor.getString(cursor.getColumnIndex("content"))
                    Log.i(TAG, "btnQueryCurrentInsert: $bean")
                }
                cursor.close()
            }
        }

        binding.btnUpdate.setOnClickListener {
            insertUri?.let {
                val id = ContentUris.parseId(insertUri)
                Log.i(TAG, "uri parseId: $id")

                val contentValues = ContentValues()
                contentValues.put("name", "bloodsoul_new")
                val updateCount = contentResolver.update(operationUri, contentValues, "sn = ?", arrayOf("$id"))
                Log.i(TAG, "updateCount: $updateCount")
            }
        }

        binding.btnDelete.setOnClickListener {
            insertUri?.let {
                val id = ContentUris.parseId(it)
                val deleteCount = contentResolver.delete(operationUri, "sn = ?", arrayOf("$id"))
                Log.i(TAG, "deleteCount: $deleteCount")
            }
        }

        binding.btnTransaction.setOnClickListener {
            val operationList = arrayListOf<ContentProviderOperation>()
            operationList.add(ContentProviderOperation.newInsert(operationUri)
                    .withValue("sn", SystemClock.uptimeMillis() % 10000)
                    .withValue("version", 2)
                    .withValue("content", "transaction${random.nextInt(100)}")
                    .build())
            val result = contentResolver.applyBatch(AUTHORITIES, operationList)
            Log.i(TAG, "transaction: $result")
        }

    }
}