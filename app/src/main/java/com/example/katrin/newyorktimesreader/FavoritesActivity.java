package com.example.katrin.newyorktimesreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity {
    ArticleRecycler recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recycler = new ArticleRecycler(this, null);
        RecyclerView favRecycler = findViewById(R.id.fav_recycler);
        favRecycler.setLayoutManager(new LinearLayoutManager(this));
        favRecycler.setAdapter(recycler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recycler.updateArticleList(ArticlesStore.getFavList(this));
    }
}
