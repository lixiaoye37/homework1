package com.example.mark.newsapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mark on 2017/6/23.
 */

public class NetworkUtils {
    private static final String NEWS_BASE_URL = "https://newsapi.org/v1/articles?";

    private static final String SOURCE = "the-next-web";
    private static final String SORT_BY = "latest";
    private static final String API_KEY = "";

    final static String SOURCE_PARAM = "source";
    final static String SORT_PARAM = "sortBy";
    final static String API_KEY_PARAM = "apiKey";

    public static URL buildUrl(String key){
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, SOURCE)
                .appendQueryParameter(SORT_PARAM, SORT_BY)
                .appendQueryParameter(API_KEY_PARAM, key)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("items");

        for(int i = 0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");
            NewsItem repo = new NewsItem(author, title,description, url,urlToImage, publishedAt);
            result.add(repo);
        }
        return result;
    }
}
