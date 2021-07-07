package com.blood.nativedemo.sqlite;

import android.net.Uri;

public class SqliteConstants {

    public static final String DATABASE_NAME = "bloodsoul.db";
    public static final int DATABASE_VERSION = 8;
    public static final String AUTHORITIES = "com.blood.nativedemo.SqliteContentProvider";

    public static final String PATH_BLOOD = "blood";
    public static final String PATH_SOUL = "soul";

    public static final int CODE_BLOOD = 111;
    public static final int CODE_SOUL = 222;

    public static final Uri URI_BASE = Uri.parse("content://" + AUTHORITIES);
    public static final Uri URI_BLOOD = Uri.withAppendedPath(URI_BASE, PATH_BLOOD);
    public static final Uri URI_SOUL = Uri.withAppendedPath(URI_BASE, PATH_SOUL);

}
