package com.vietxongxoa.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vietxongxoa.R;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.ui.viewholder.LoadMoreRecyclerViewAdapter;
import com.vietxongxoa.ui.viewholder.PostViewHolder;
import com.vietxongxoa.ui.viewholder.WriteViewHolder;

public class CommentAdapter extends LoadMoreRecyclerViewAdapter<Object>{

    private Context mContext;
    protected CommentAdapter(@NonNull Context context, ItemClickListener itemClickListener, @NonNull RetryLoadMoreListener retryLoadMoreListener) {
        super(context, itemClickListener, retryLoadMoreListener);
        mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseItem.HEADER_TYPE) {
            View view = mInflater.inflate(R.layout.post_item, parent, false);
            return new WriteViewHolder(view);
        } else if (viewType == BaseItem.SECOND_TYPE){
            View view = mInflater.inflate(R.layout.post_item, parent, false);
            return new PostViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);

    }

    @Override
    protected int getCustomItemViewType(int position) {
        return 0;
    }
}
