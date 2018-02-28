package com.example.katrin.newyorktimesreader;

public class Article {

    private String fullTextUrl;
    private String section;
    private String byLine;
    private String title;
    private String abstractText;
    private String published_date;
    private String imageUrl;

    Article(String fullTextUrl, String section, String byLine, String title, String abstractText, String published_date, String imageUrl) {
        this.fullTextUrl = fullTextUrl;
        this.section = section;
        this.byLine = byLine;
        this.title = title;
        this.abstractText = abstractText;
        this.published_date = published_date;
        this.imageUrl = imageUrl;
    }

    public String getSection() {
        return section;
    }

    String getByLine() {
        return byLine;
    }

    public String getTitle() {
        return title;
    }

    String getAbstractText() {
        return abstractText;
    }

    String getPublished_date() {
        return published_date;
    }

    String getImageUrl() {
        return imageUrl;
    }

}
