package com.example.mark.newsapp.database;

import android.provider.BaseColumns;

/**
 * Created by Mark on 2017/7/27.
 */
//create table name and name each comlumn
public class Contract {
    public static class TABLE_NEWS implements BaseColumns {

        public static final String TABLE_NAME="article";

        public static final String COLUMN_NAME_AUTHOR="author";

        public static final String COLUMN_NAME_TITLE="title";

        public static final String COLUMN_NAME_DESCRIPTION="description";

        public static final String COLUMN_NAME_WEB_URL="web_url";

        public static final String COLUMN_NAME_IMG_URL="imgage_url";

        public static final String COLUMN_NAME_PUBLISHEDAT="publishedAt";

    }
}
