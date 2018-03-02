package com.example.katrin.newyorktimesreader.SQLite;

import android.content.ContentValues;
import android.content.Context;

public interface Repository<T, ID> {

    void open(Context context);

    void close();

    long add(T item);

    void delete(T item);

    T update(T item);

    java.util.List<T> getAll();

    //    java.util.List<T> getAllDesc();
    ContentValues itemToContentValues(T item);

    T findById(ID id);
}
