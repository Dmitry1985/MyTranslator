package com.example.yandextranslator.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 21.04.2017.
 */

public class Db {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sDb;

    private static final String DB_NAME = "languages";
    private static final int VERSION = 6;

    public static final String TABLE_NAME = "langlist";

    public static final String LANG_NAME = "name";
    public static final String LANG_CODE = "code";


    public Db(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Cursor getAllItems() {

        sDb = dbHelper.getWritableDatabase();
        return sDb.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void close() {
        if (dbHelper != null) dbHelper.close();
        if (sDb != null) sDb.close();
    }


    public class DatabaseHelper extends SQLiteOpenHelper {



        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + LANG_NAME + " TEXT, " + LANG_CODE + " TEXT" + ");");

            ContentValues cv = new ContentValues();


            Languages x = new Languages();
            Map <String, String> hashMap = x.getMyHash();

            for (Map.Entry entry: hashMap.entrySet() )
            {

                cv.put(LANG_NAME, entry.getKey().toString() );
                cv.put(LANG_CODE, entry.getValue().toString());
                db.insert(TABLE_NAME, null, cv);

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            this.onCreate(db);
        }

    }

}