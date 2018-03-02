package com.example.katrin.newyorktimesreader.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.katrin.newyorktimesreader.Article;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository implements Repository<Article, String> {

    private FavoritesDBHelper dbHelper;

    @Override
    public void open(Context context) {
        dbHelper = new FavoritesDBHelper(context);
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public long add(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(DBContract.FavoritesTable.TABLE_NAME, null, itemToContentValues(article));
    }

    @Override
    public void delete(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBContract.FavoritesTable.TABLE_NAME, DBContract.FavoritesTable._ID + " = ? ",
                new String[]{article.getFullTextUrl()});

    }

    @Override
    public Article update(Article article) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DBContract.FavoritesTable.TABLE_NAME, itemToContentValues(article),
                DBContract.FavoritesTable._ID + " = ? ", new String[]{article.getFullTextUrl()});
        return findById(article.getFullTextUrl());

    }

    @Override
    public List<Article> getAll() {
        List<Article> articleList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBContract.FavoritesTable.TABLE_NAME, null, null,
                null, null, null, null);

        int idx_id = cursor.getColumnIndex(DBContract.FavoritesTable._ID);
        int idx_byline = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_BYLINE);
        int idx_title = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_TITLE);
        int idx_abstract = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_ABSTRACT);
        int idx_published_date = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_PUBLISHED_DATE);
        int idx_image_url = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_IMAGE_URL);

        Article article;
        while (cursor.moveToNext()) {
            article = new Article(cursor.getString(idx_id), cursor.getString(idx_byline), cursor.getString(idx_title),
                    cursor.getString(idx_abstract), cursor.getString(idx_published_date), cursor.getString(idx_image_url));
            articleList.add(article);
        }
        cursor.close();

        return articleList;

    }

    @Override
    public ContentValues itemToContentValues(Article article) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.FavoritesTable._ID, article.getFullTextUrl());
        contentValues.put(DBContract.FavoritesTable.COLUMN_NAME_BYLINE, article.getByLine());
        contentValues.put(DBContract.FavoritesTable.COLUMN_NAME_TITLE, article.getTitle());
        contentValues.put(DBContract.FavoritesTable.COLUMN_NAME_ABSTRACT, article.getAbstractText());
        contentValues.put(DBContract.FavoritesTable.COLUMN_NAME_PUBLISHED_DATE, article.getPublished_date());
        contentValues.put(DBContract.FavoritesTable.COLUMN_NAME_IMAGE_URL, article.getImageUrl());

        return contentValues;
    }

    @Override
    public Article findById(String id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DBContract.FavoritesTable.TABLE_NAME, null, DBContract.FavoritesTable._ID + " = ? ",
                new String[]{id}, null, null, null);
        int idx_id = cursor.getColumnIndex(DBContract.FavoritesTable._ID);
        int idx_byline = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_BYLINE);
        int idx_title = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_TITLE);
        int idx_abstract = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_ABSTRACT);
        int idx_published_date = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_PUBLISHED_DATE);
        int idx_image_url = cursor.getColumnIndex(DBContract.FavoritesTable.COLUMN_NAME_IMAGE_URL);

        Article article = null;
        if (cursor.moveToNext()) {
            article = new Article(cursor.getString(idx_id), cursor.getString(idx_byline), cursor.getString(idx_title),
                    cursor.getString(idx_abstract), cursor.getString(idx_published_date), cursor.getString(idx_image_url));
        }
        cursor.close();
        return article;

    }
}
