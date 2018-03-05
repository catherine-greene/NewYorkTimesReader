package com.example.katrin.newyorktimesreader;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleRecycler extends RecyclerView.Adapter<ArticleRecycler.ViewHolder> {

    private List<Article> articleList;
    private Context context;

    ArticleRecycler(ArticlesStore.Category category, Context context) {
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

    ArticleRecycler(Context context, List<Article> articleList) {
        this.articleList = articleList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Article article = articleList.get(position);

        if (article.imageUrl == null) {
            holder.imageView.setVisibility(View.GONE);
        } else {
            Picasso.with(context)
                    .load(article.imageUrl)
                    .into(holder.imageView);
        }
        holder.title.setText(article.title);
        holder.byline.setText(article.byLine);
        holder.publishedDate.setText(article.published_date);
        holder.abstractText.setText(article.abstractText);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), FullTextActivity.class);
                intent.putExtra("article", article);
                context.startActivity(intent);

            }
        });

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
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            byline = itemView.findViewById(R.id.byline);
            publishedDate = itemView.findViewById(R.id.published_date);
            abstractText = itemView.findViewById(R.id.abstractText);
        }
    }


}
