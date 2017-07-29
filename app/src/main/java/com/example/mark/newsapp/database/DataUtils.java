package com.example.mark.newsapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mark.newsapp.NewsItem;

import java.util.ArrayList;

import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR;
import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION;
import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.COLUMN_NAME_IMG_URL;
import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHEDAT;
import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.COLUMN_NAME_TITLE;
import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.COLUMN_NAME_WEB_URL;
import static com.example.mark.newsapp.database.Contract.TABLE_NEWS.TABLE_NAME;

/**
 * Created by Mark on 2017/7/27.
 */

public class DataUtils {

    public static void insertData(SQLiteDatabase db, ArrayList<NewsItem> newsItems) {
        // insert news to database
        db.beginTransaction();
        try {
            for (NewsItem newsItem : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_AUTHOR, newsItem.getAuthor());
                cv.put(COLUMN_NAME_TITLE, newsItem.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, newsItem.getDescription());
                cv.put(COLUMN_NAME_WEB_URL, newsItem.getUrl());
                cv.put(COLUMN_NAME_IMG_URL, newsItem.getUrlToImage());
                cv.put(COLUMN_NAME_PUBLISHEDAT,newsItem.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    //get data from database
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHEDAT + " DESC"
        );
        return cursor;
    }
  //Delete everything from database
    public static void deleteAll(SQLiteDatabase db) {
        //method for delete database
        db.delete(TABLE_NAME, null, null);
    }

}
