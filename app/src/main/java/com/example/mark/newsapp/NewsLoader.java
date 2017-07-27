package com.example.mark.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Mark on 2017/7/25.
 */

public class NewsLoader extends AsyncTaskLoader<List<String>> {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    public List<String> loadInBackground() {
        return null;
    }
}
