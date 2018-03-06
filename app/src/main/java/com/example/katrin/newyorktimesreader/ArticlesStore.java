package com.example.katrin.newyorktimesreader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.katrin.newyorktimesreader.SQLite.FavoritesRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ArticlesStore {

    private static final ArticlesStore ourInstance = new ArticlesStore();
    private static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";
    private static final String API_KEY = "84360f4cbd464edb862b7d2199a79fac";
    private static final String MOST_EMAILED = "mostemailed";
    private static final String MOST_VIEWED = "mostviewed";
    private static final String MOST_SHARED = "mostshared";

    private List<Article> favList = new ArrayList<>();

    private Subject<List<Article>> emailedObservable = BehaviorSubject.create();
    private Subject<List<Article>> sharedObservable = BehaviorSubject.create();
    private Subject<List<Article>> viewedObservable = BehaviorSubject.create();

    //    private List<Article> emailedList = new ArrayList<>();
//    private ArticleRecycler emailedRecycler;
    private List<Article> sharedList = new ArrayList<>();
    private ArticleRecycler sharedRecycler;
    private List<Article> viewedList = new ArrayList<>();
    private ArticleRecycler viewedRecycler;

    private ArticlesStore() {

        requestArticles(Category.mostEmailed);
        requestArticles(Category.mostShared);
        requestArticles(Category.mostViewed);
    }

    static Observable<List<Article>> getObservable(Category category) {
        switch (category) {
            case mostEmailed:
                return ourInstance.emailedObservable;
            case mostShared:
                return ourInstance.sharedObservable;
            default:
                return ourInstance.viewedObservable;
        }
    }

    static void sync() {
        ourInstance.requestArticles(Category.mostEmailed);
        ourInstance.requestArticles(Category.mostShared);
        ourInstance.requestArticles(Category.mostViewed);
    }

    static List<Article> getFavList(Context context) {
        FavoritesRepository repository = new FavoritesRepository();
        repository.open(context);
        ourInstance.favList = repository.getAll();
        repository.close();
        return ourInstance.favList;
    }

    private void requestArticles(Category category) {
        Call<Result> resultCall;
        switch (category) {
            case mostEmailed:
                resultCall = getMostPopularArticlesService().getMostPopular(MOST_EMAILED, API_KEY);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        Result result = response.body();
                        if (result != null) {
                            emailedObservable.onNext(result.articleList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                        emailedObservable.onError(t);
                    }
                });
                break;
            case mostShared:
                resultCall = getMostPopularArticlesService().getMostPopular(MOST_SHARED, API_KEY);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        Result result = response.body();
                        if (result != null) {
                            sharedObservable.onNext(result.articleList);

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                        sharedObservable.onError(t);
                    }
                });
                break;
            case mostViewed:
                resultCall = getMostPopularArticlesService().getMostPopular(MOST_VIEWED, API_KEY);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        Result result = response.body();
                        if (result != null) {
                            viewedObservable.onNext(result.articleList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                        viewedObservable.onError(t);
                    }
                });


        }
    }

    private MostPopularArticlesService getMostPopularArticlesService() {
        Retrofit retrofitClient = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();


        return retrofitClient.create(MostPopularArticlesService.class);
    }

    enum Category {
        mostEmailed, mostShared, mostViewed
    }
}
