package com.example.katrin.newyorktimesreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ArticleListFragment extends Fragment implements Observer<List<Article>> {

    private ArticlesStore.Category category;
    private ArticleRecycler articleRecycler;
    private Disposable subscription;

    public ArticleListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_most_emailed, container, false);

        Bundle bundle = getArguments();
        category = (ArticlesStore.Category) bundle.getSerializable("category");

        articleRecycler = new ArticleRecycler(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.most_emailed_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(articleRecycler);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArticlesStore.getObservable(category).subscribe(this);
    }

    @Override
    public void onPause() {
        if (subscription != null) {
            subscription.dispose();
        }
        super.onPause();
    }


    @Override
    public void onSubscribe(Disposable d) {
        subscription = d;
    }

    @Override
    public void onNext(List<Article> articleList) {
        articleRecycler.updateArticleList(articleList);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
