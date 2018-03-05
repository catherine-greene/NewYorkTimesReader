package com.example.katrin.newyorktimesreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.katrin.newyorktimesreader.SQLite.FavoritesRepository;

public class FullTextActivity extends AppCompatActivity {

    private boolean favCheck = false;
    private Article article;
    private FavoritesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_text);

        WebView webView = findViewById(R.id.web_view);
        article = (Article) getIntent().getSerializableExtra("article");
        webView.setWebViewClient(new WebViewClient());
        webView.setClickable(false);
        webView.loadUrl(article.fullTextUrl);

        repository = new FavoritesRepository();
        repository.open(this);
        if (repository.findById(article.fullTextUrl) != null) {
            favCheck = true;
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_fav_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.findItem(R.id.add_to_fav);

        if (favCheck) {
            menuItem.setIcon(R.drawable.favorites_icon);
        } else {
            menuItem.setIcon(android.R.drawable.btn_star_big_off);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_to_fav:

                if (favCheck) {
                    favCheck = false;
                    repository.delete(article);
                } else {
                    favCheck = true;
                    repository.add(article);
                }
                invalidateOptionsMenu();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        repository.close();
        super.onDestroy();
    }
}
