package com.example.kr12rpois;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "odnockassniki2";
    public static final String TABLE_NAME = "odnockassniki1234";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_FIRST_NAME = "firstname";
    public static final String KEY_SECOND_NAME = "secondname";
    public static final String KEY_TIME = "time";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" + KEY_ID + " integer primary key,"
                + KEY_NAME + " text," + KEY_TIME + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion <= 1) {

            db.beginTransaction();
            try {
                db.execSQL("create table " + TABLE_NAME + "("
                        + KEY_ID + " integer primary key,"
                        + KEY_FIRST_NAME + " text,"
                        + KEY_NAME + " text,"
                        + KEY_SECOND_NAME + " text,"
                        + KEY_TIME + " text" + ")");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}
