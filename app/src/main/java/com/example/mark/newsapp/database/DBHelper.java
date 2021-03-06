package com.example.mark.newsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mark on 2017/7/27.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="articles.db";
    private static final String TAG="dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString="CREATE TABLE " + Contract.TABLE_NEWS.TABLE_NAME+ " ("+
                Contract.TABLE_NEWS._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.TABLE_NEWS.COLUMN_NAME_TITLE+" TEXT NOT NULL, "+
                Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR+" TEXT NOT NULL, "+
                Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION+" TEXT NOT NULL, "+
                Contract.TABLE_NEWS.COLUMN_NAME_WEB_URL+" TEXT NOT NULL, "+
                Contract.TABLE_NEWS.COLUMN_NAME_IMG_URL+" TEXT NOT NULL, "+
                Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHEDAT+" DATE "+
                "); ";


        Log.d(TAG,"Created Table: "+queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE "+Contract.TABLE_NEWS.TABLE_NAME+" if exists;");
    }
}