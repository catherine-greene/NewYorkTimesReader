package com.example.katrin.newyorktimesreader;

import java.util.ArrayList;
import java.util.List;

public class ArticlesStore implements GetArticlesTask.OnTaskResponse {

    private static final ArticlesStore ourInstance = new ArticlesStore();
    private List<Article> emailedList = new ArrayList<>();
    private List<Article> sharedList = new ArrayList<>();
    private List<Article> viewedList = new ArrayList<>();

    private ArticleRecycler emailedRecycler;
    private ArticleRecycler sharedRecycler;
    private ArticleRecycler viewedRecycler;

    private ArticlesStore() {
        new GetArticlesTask(this).execute(GetArticlesTask.Category.mostEmailed);
        new GetArticlesTask(this).execute(GetArticlesTask.Category.mostShared);
        new GetArticlesTask(this).execute(GetArticlesTask.Category.mostViewed);
    }

    static List<Article> getEmailedList(ArticleRecycler recycler) {
        ourInstance.emailedRecycler = recycler;
        return ourInstance.emailedList;
    }

    static List<Article> getSharedList(ArticleRecycler recycler) {
        ourInstance.viewedRecycler = recycler;
        return ourInstance.sharedList;
    }

    static List<Article> getViewedList(ArticleRecycler recycler) {
        ourInstance.viewedRecycler = recycler;
        return ourInstance.viewedList;
    }

    @Override
    public void createArticlesList(List<Article> articleList, GetArticlesTask.Category category) {

        switch (category) {
            case mostEmailed:
                emailedList = articleList;
                if (emailedRecycler != null) {
                    emailedRecycler.updateArticleList(articleList);
                }
                break;
            case mostShared:
                sharedList = articleList;
                if (sharedRecycler != null) {
                    sharedRecycler.updateArticleList(articleList);
                }
                break;
            case mostViewed:
                viewedList = articleList;
                if (viewedRecycler != null) {
                    viewedRecycler.updateArticleList(articleList);
                }
                break;
        }
    }
}
