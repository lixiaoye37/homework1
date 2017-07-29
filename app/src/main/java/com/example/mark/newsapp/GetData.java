package com.example.mark.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mark.newsapp.database.DBHelper;
import com.example.mark.newsapp.database.DataUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mark on 2017/7/28.
 */
// get the data from news web
public class GetData {

    public static void refreshNews(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl("25a5cb4b4d0f4e69964b1c735c87485f");
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        try {
            //delete data
            DataUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DataUtils.insertData(db, result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}
