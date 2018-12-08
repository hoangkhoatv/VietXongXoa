package com.vietxongxoa.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.detail.DetailActivity;
import com.vietxongxoa.ui.main.ItemInteractiveListener;
import com.vietxongxoa.ui.viewholder.LoadMoreRecyclerViewAdapter;
import com.vietxongxoa.ui.viewholder.PostViewHolder;
import com.vietxongxoa.ui.viewholder.WriteViewHolder;
import com.vietxongxoa.ui.write.WriteActivity;


public class PostAdapter extends LoadMoreRecyclerViewAdapter<Object> {

    private Context mContext;
    private ItemInteractiveListener itemInteractiveListener;

    public PostAdapter(Context context, ItemClickListener itemClickListener,
                       RetryLoadMoreListener retryLoadMoreListener, ItemInteractiveListener itemInteractiveListener) {
        super(context, itemClickListener, retryLoadMoreListener);
        mContext = context;
        this.itemInteractiveListener = itemInteractiveListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseItem.HEADER_TYPE) {
            View view = mInflater.inflate(R.layout.item_write_new_post, parent, false);
            return new WriteViewHolder(view);
        } else if (viewType == BaseItem.SECOND_TYPE) {
            View view = mInflater.inflate(R.layout.item_post_in_list, parent, false);
            return new PostViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);

    }

    @Override
    protected int getCustomItemViewType(int position) {
        Data<BaseItem> baseItem = (Data<BaseItem>) mDataList.get(position);
        return baseItem.attributes.type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof PostViewHolder) {
            final Data<PostItem> data = (Data<PostItem>) mDataList.get(position);
            final PostItem item = data.attributes;
            ((PostViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = DetailActivity.getStartIntent(mContext);
                    intent.putExtra(PreferencesHelper.KEY_ID, data.uuid);
                    intent.putExtra(PreferencesHelper.KEY_AUTHOR, item.author);
                    intent.putExtra(PreferencesHelper.KEY_CONTENT, item.content);
                    intent.putExtra(PreferencesHelper.KEY_DATE, item.created);
                    intent.putExtra(PreferencesHelper.KEY_NUM_LOVE, item.love);
                    intent.putExtra(PreferencesHelper.KEY_COMMENT, item.comment);
                    intent.putExtra(PreferencesHelper.KEY_LOVED, item.loved);
                    mContext.startActivity(intent);
                }

                @Override
                public void onLove(View view, int pos, boolean isLove) {
                    final Data<PostItem> data = (Data<PostItem>) mDataList.get(pos);
                    itemInteractiveListener.onLove(data.uuid, isLove, position);
                }

                @Override
                public void onComment(View view, int position) {

                }
            });
            ((PostViewHolder) holder).setData(item, mContext);
        } else if (holder instanceof WriteViewHolder) {
            ((WriteViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    mContext.startActivity(WriteActivity.getStartIntent(mContext));
                }

                @Override
                public void onLove(View view, int position, boolean isLove) {

                }

                @Override
                public void onComment(View view, int position) {

                }
            });
        }
        super.onBindViewHolder(holder, position);

    }

    public void loved(int pos) {
        ((Data<PostItem>) mDataList.get(pos)).attributes.loved = true;
        int love = Integer.parseInt(((Data<PostItem>) mDataList.get(pos)).attributes.love) + 1;
        ((Data<PostItem>) mDataList.get(pos)).attributes.love = String.valueOf(love);
        notifyItemChanged(pos);
    }

    public void unLove(int pos) {
        ((Data<PostItem>) mDataList.get(pos)).attributes.loved = false;
        int love = Integer.parseInt(((Data<PostItem>) mDataList.get(pos)).attributes.love) - 1;
        ((Data<PostItem>) mDataList.get(pos)).attributes.love = String.valueOf(love);
        notifyItemChanged(pos);
    }
}
