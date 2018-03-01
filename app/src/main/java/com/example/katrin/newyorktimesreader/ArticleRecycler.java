package com.example.katrin.newyorktimesreader;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ArticleRecycler extends RecyclerView.Adapter<ArticleRecycler.ViewHolder> {

    private List<Article> articleList;
    private Context context;

    ArticleRecycler(GetArticlesTask.Category category, Context context) {
        switch (category) {
            case mostEmailed:
                articleList = ArticlesStore.getEmailedList(this);
                break;
            case mostShared:
                articleList = ArticlesStore.getSharedList(this);
                break;
            case mostViewed:
                articleList = ArticlesStore.getViewedList(this);
                break;
        }
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Article article = articleList.get(position);

//        Picasso.with(context)
//                .load(article.getImageUrl())
//                .centerCrop()
//                .into(holder.imageView);
        holder.title.setText(article.getTitle());
        holder.byline.setText(article.getByLine());
        holder.publishedDate.setText(article.getPublished_date());
        holder.abstractText.setText(article.getAbstractText());

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    void updateArticleList(List<Article> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView byline;
        TextView publishedDate;
        TextView abstractText;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            byline = itemView.findViewById(R.id.byline);
            publishedDate = itemView.findViewById(R.id.published_date);
            abstractText = itemView.findViewById(R.id.abstractText);
        }
    }


}
