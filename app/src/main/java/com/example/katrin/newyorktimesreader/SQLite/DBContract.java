package com.example.katrin.newyorktimesreader.SQLite;

import android.provider.BaseColumns;

class DBContract {
    static final String DB_NAME = "favorites_db";
    static final int DB_VERSION = 1;
    private static final String COMMA_SEP = ", ";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String UNIQUE = " UNIQUE";


    class FavoritesTable implements BaseColumns {
        static final String TABLE_NAME = "favorites ";
        static final String COLUMN_NAME_BYLINE = "byline";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_ABSTRACT = "abstract";
        static final String COLUMN_NAME_PUBLISHED_DATE = "published_date";
        static final String COLUMN_NAME_IMAGE_URL = "image_url";

        static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" + _ID + TEXT_TYPE + PRIMARY_KEY + UNIQUE + COMMA_SEP
                + COLUMN_NAME_BYLINE + TEXT_TYPE + NOT_NULL + COMMA_SEP
                + COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP
                + COLUMN_NAME_ABSTRACT + TEXT_TYPE + NOT_NULL + COMMA_SEP
                + COLUMN_NAME_PUBLISHED_DATE + TEXT_TYPE + NOT_NULL + COMMA_SEP
                + COLUMN_NAME_IMAGE_URL + TEXT_TYPE
                + ")";
    }
}
