package com.vietxongxoa.ui;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.detail.DetailActivity;
import com.vietxongxoa.ui.viewholder.ItemClickListener;
import com.vietxongxoa.ui.viewholder.LoadMoreRecyclerViewAdapter;
import com.vietxongxoa.ui.viewholder.PostViewHolder;
import com.vietxongxoa.ui.viewholder.WriteViewHolder;
import com.vietxongxoa.ui.write.WriteActivity;


public class MyAdapter extends LoadMoreRecyclerViewAdapter<Object> {

    private Context mContext;
    public MyAdapter(Context context, ItemClickListener itemClickListener,
                     RetryLoadMoreListener retryLoadMoreListener) {
        super(context, itemClickListener, retryLoadMoreListener);
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseItem.HEADER_TYPE) {
            View view = mInflater.inflate(R.layout.write_item, parent, false);
            return new WriteViewHolder(view);
        } else if (viewType == BaseItem.SECOND_TYPE){
            View view = mInflater.inflate(R.layout.post_item, parent, false);
            return new PostViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);

    }

    @Override
    protected int getCustomItemViewType(int position) {
        BaseItem baseItem = (BaseItem) mDataList.get(position);
        return baseItem.type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            final PostItem item = (PostItem) mDataList.get(position);
            ((PostViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = DetailActivity.getStartIntent(mContext);
                    intent.putExtra(PreferencesHelper.KEY_AUTHOR,item.author);
                    intent.putExtra(PreferencesHelper.KEY_CONTENT,item.content);
                    intent.putExtra(PreferencesHelper.KEY_DATE,item.created);
                    mContext.startActivity(intent);
                }
            });
            ((PostViewHolder) holder).setData(item);
        } else if(holder instanceof  WriteViewHolder){
            ((WriteViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    mContext.startActivity(WriteActivity.getStartIntent(mContext));
                }
            });
        }
        super.onBindViewHolder(holder, position);

    }


}
