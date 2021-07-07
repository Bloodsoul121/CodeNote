package com.blood.nativedemo.sqlite;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.blood.nativedemo.sqlite.SqliteConstants.AUTHORITIES;
import static com.blood.nativedemo.sqlite.SqliteConstants.CODE_BLOOD;
import static com.blood.nativedemo.sqlite.SqliteConstants.CODE_SOUL;
import static com.blood.nativedemo.sqlite.SqliteConstants.PATH_BLOOD;
import static com.blood.nativedemo.sqlite.SqliteConstants.PATH_SOUL;

public class SqliteContentProvider extends ContentProvider {

    private static final String TAG = "SqliteContentProvider";

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITIES, PATH_BLOOD, CODE_BLOOD);
        sUriMatcher.addURI(AUTHORITIES, PATH_SOUL, CODE_SOUL);
    }

    private SQLiteDatabase mDatabase;
    private SqliteHelper mSqliteHelper;
    private final AtomicInteger mOpenCounter = new AtomicInteger();

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate: ");
        mSqliteHelper = new SqliteHelper(getContext());
        return true;
    }



    private SQLiteDatabase openDb() {
        Log.i(TAG, "openDb: ");
        if (mDatabase == null) {
            try {
                mDatabase = mSqliteHelper.getWritableDatabase();
            } catch (Exception e) {
                mDatabase = mSqliteHelper.getReadableDatabase();
            }
        }
        return mDatabase;
    }

    private synchronized SQLiteDatabase syncOpenDb() {
        Log.i(TAG, "openDb: ");
        if (mOpenCounter.incrementAndGet() == 1) {
            try {
                mDatabase = mSqliteHelper.getWritableDatabase();
            } catch (Exception e) {
                mDatabase = mSqliteHelper.getReadableDatabase();
            }
        }
        return mDatabase;
    }

    private synchronized void closeDb() {
            if (mDatabase != null) {
                mDatabase.close();
            }
    }

    private synchronized void syncCloseDb() {
        if (mOpenCounter.decrementAndGet() == 0) {
            if (mDatabase != null) {
                mDatabase.close();
            }
        }
    }

    /**
     * @param uri           数据库路径
     * @param projection    查询列项
     * @param selection     查询条件
     * @param selectionArgs 查询条件参数
     * @param sortOrder     排序
     * @return 游标
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(TAG, "query: " + uri);
        Cursor cursor = null;
        SQLiteDatabase database = openDb();
        switch (sUriMatcher.match(uri)) {
            case CODE_BLOOD:
                cursor = database.query(PATH_BLOOD, projection, selection, selectionArgs, null, null, sortOrder, null);
                break;
            case CODE_SOUL:
                cursor = database.query(PATH_SOUL, projection, selection, selectionArgs, null, null, sortOrder, null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(TAG, "getType: " + uri.toString());
        switch (sUriMatcher.match(uri)) {
            case CODE_BLOOD:
                return "vnd.android.cursor.dir/blood";
            case CODE_SOUL:
                return "vnd.android.cursor.dir/soul";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri resultUri = null;
        long insertId = 0;
        SQLiteDatabase database = openDb();
        switch (sUriMatcher.match(uri)) {
            case CODE_BLOOD:
                insertId = database.insertWithOnConflict(PATH_BLOOD, null, values, CONFLICT_REPLACE);
//                insertId = database.insertOrThrow(PATH_BLOOD, null, values);
//                insertId = database.insert(PATH_BLOOD, "name", values);
//                insertId = database.insert(PATH_BLOOD, null, values);
                if (insertId > 0) {
                    resultUri = ContentUris.withAppendedId(uri, insertId);
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            case CODE_SOUL:
                insertId = database.insert(PATH_SOUL, "name", values);
                if (insertId > 0) {
                    resultUri = ContentUris.withAppendedId(uri, insertId);
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
        }
        Log.i(TAG, "insert: insertId " + insertId);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase database = openDb();
        switch (sUriMatcher.match(uri)) {
            case CODE_BLOOD:
                count = database.delete(PATH_BLOOD, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            case CODE_SOUL:
                count = database.delete(PATH_SOUL, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase database = openDb();
        switch (sUriMatcher.match(uri)) {
            case CODE_BLOOD:
                count = database.update(PATH_BLOOD, values, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            case CODE_SOUL:
                count = database.update(PATH_SOUL, values, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
        }
        return count;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = openDb();
        db.beginTransaction();//开始事务
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();//设置事务标记为successful
            return results;
        } finally {
            db.endTransaction();//结束事务
        }
    }
}
