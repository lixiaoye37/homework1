package com.example.mark.newsapp;




import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mark.newsapp.database.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lixiaoye37 on 2017/6/29.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {
    private Cursor mCursor;
    private ItemClickListener listener;
    private Context mContext;




    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.mCursor = cursor;
        this.listener = listener;
    }




    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public TextView title;
        public TextView desc;
        public TextView publishedAt;

        public NewsAdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.description);
            publishedAt = (TextView) view.findViewById(R.id.publish_at);
            image = (ImageView) view.findViewById(R.id.iv_img);
            view.setOnClickListener(this);
        }
        public void bind(int pos) {
            mCursor.moveToPosition(pos);
            title.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_TITLE)));
            desc.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION)));
            publishedAt.setText(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHEDAT)));
            String url = mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_IMG_URL));
           //use picasso for image display
            if(url != null){
                Picasso.with(mContext)
                        .load(url)
                        .into(image);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(mCursor, pos);
        }

    }
    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
        return mCursor.getCount();
    }



}
