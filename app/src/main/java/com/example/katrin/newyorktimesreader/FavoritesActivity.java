package com.example.katrin.newyorktimesreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ArticleRecycler recycler = new ArticleRecycler(this, ArticlesStore.getFavList(this));
        RecyclerView favRecycler = findViewById(R.id.fav_recycler);
        favRecycler.setLayoutManager(new LinearLayoutManager(this));
        favRecycler.setAdapter(recycler);
    }
}
