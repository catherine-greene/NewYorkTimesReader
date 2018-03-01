package com.example.katrin.newyorktimesreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FullTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_text);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        WebView webView = findViewById(R.id.web_view);
        String url = getIntent().getStringExtra(ArticleRecycler.FULL_TEXT_URL);
        webView.setWebViewClient(new WebViewClient());
        webView.setClickable(false);
        webView.loadUrl(url);
    }
}
