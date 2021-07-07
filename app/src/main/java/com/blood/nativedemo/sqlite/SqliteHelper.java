package com.blood.nativedemo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.blood.nativedemo.sqlite.SqliteConstants.DATABASE_NAME;
import static com.blood.nativedemo.sqlite.SqliteConstants.DATABASE_VERSION;
import static com.blood.nativedemo.sqlite.SqliteConstants.PATH_BLOOD;
import static com.blood.nativedemo.sqlite.SqliteConstants.PATH_SOUL;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SqliteHelper";

    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: ");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PATH_BLOOD + " ( "
                + "sn INTEGER PRIMARY KEY,"
                + "name TEXT,"
                + "version INTEGER DEFAULT 1,"
                + "content TEXT NOT NULL,"
                + "timestamp INTEGER"
                + " )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + PATH_SOUL + " ( "
                + "sn INTEGER,"
                + "name TEXT,"
                + "version INTEGER DEFAULT 1,"
                + "content TEXT,"
                + "timestamp INTEGER"
                + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: ");
        dropAll(db);
        onCreate(db);
    }

    private void dropAll(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + PATH_BLOOD);
        db.execSQL("DROP TABLE IF EXISTS " + PATH_SOUL);
    }
}
