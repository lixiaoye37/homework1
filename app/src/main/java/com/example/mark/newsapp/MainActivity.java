package com.example.mark.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.newsapp.database.Contract;
import com.example.mark.newsapp.database.DBHelper;
import com.example.mark.newsapp.database.DataUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Void>,NewsAdapter.ItemClickListener{

    static final String TAG = "mainactivity";
    private RecyclerView newsRecyclerView;
    private static final int NEWS_LOADER = 1;
    private SQLiteDatabase db;
    private Cursor cursor;
    private NewsAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //check the if the first time already run
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);
        //if already run the first time load news to database
        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        getSupportLoaderManager().initLoader(NEWS_LOADER, null, this);

        // schedule updates
        ScheduleUtilities.scheduleRefresh(this);

    }
    //when app starts get data from database
    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DataUtils.getAll(db);
        mAdapter = new NewsAdapter(cursor, this);
        newsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }
// use asyntackloader to get data from web to database
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

            }
            @Override
            public Void loadInBackground() {
                //RefreshTasks.refreshNews(MainActivity.this);
                return null;
            }

        };
    }
// load data from database to display
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

        db=new DBHelper(MainActivity.this).getReadableDatabase();
        cursor= DataUtils.getAll(db);
        mAdapter = new NewsAdapter(cursor, this);

        newsRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
//click open link
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_WEB_URL));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//refresh button

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_refresh) {
            load();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }
}
