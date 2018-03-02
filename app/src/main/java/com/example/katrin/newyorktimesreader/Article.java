package com.example.katrin.newyorktimesreader;

import java.io.Serializable;

public class Article implements Serializable {

    private String fullTextUrl;
    private String byLine;
    private String title;
    private String abstractText;
    private String published_date;
    private String imageUrl;

    public Article(String fullTextUrl, String byLine, String title, String abstractText, String published_date, String imageUrl) {
        this.fullTextUrl = fullTextUrl;
        this.byLine = byLine;
        this.title = title;
        this.abstractText = abstractText;
        this.published_date = published_date;
        this.imageUrl = imageUrl;
    }

    public String getFullTextUrl() {
        return fullTextUrl;
    }


    public String getByLine() {
        return byLine;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public String getPublished_date() {
        return published_date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
