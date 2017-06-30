package com.example.mark.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lixiaoye37 on 2017/6/29.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {


    private ArrayList<NewsItem> newsInfo;
    ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> list, ItemClickListener listener) {
        this.newsInfo = list;
        this.listener = listener;
    }
    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }


    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView title;
        public TextView desc;
        public TextView publishedAt;

        public NewsAdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.news_title);
            desc = (TextView) view.findViewById(R.id.description);
            publishedAt = (TextView) view.findViewById(R.id.published_at);
            view.setOnClickListener(this);
        }
        public void bind(int pos) {
            NewsItem item = newsInfo.get(pos);
            title.setText(item.getTitle());
            desc.setText(item.getDescription());
            publishedAt.setText(item.getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }

    }
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new NewsAdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        if (newsInfo == null) {
            return 0;
        }
        return newsInfo.size();
    }


}
