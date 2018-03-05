package com.example.katrin.newyorktimesreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MostEmailedFragment extends Fragment {

    public MostEmailedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_most_emailed, container, false);

        ArticleRecycler articleRecycler = new ArticleRecycler(ArticlesStore.Category.mostEmailed, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.most_emailed_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(articleRecycler);

        return view;
    }
}
