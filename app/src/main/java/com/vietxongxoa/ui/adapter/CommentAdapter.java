package com.vietxongxoa.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vietxongxoa.R;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.CommentItem;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.ui.main.ItemInteractiveListener;
import com.vietxongxoa.ui.viewholder.CommentViewHolder;
import com.vietxongxoa.ui.viewholder.LoadMoreRecyclerViewAdapter;
import com.vietxongxoa.ui.viewholder.PostDetailViewHolder;

public class CommentAdapter extends LoadMoreRecyclerViewAdapter<Object> {

    private Context mContext;
    private ItemInteractiveListener itemInteractiveListener;

    public CommentAdapter(@NonNull Context context, ItemClickListener itemClickListener, @NonNull RetryLoadMoreListener retryLoadMoreListener, ItemInteractiveListener itemInteractiveListener) {
        super(context, itemClickListener, retryLoadMoreListener);
        this.itemInteractiveListener = itemInteractiveListener;
        mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseItem.HEADER_TYPE) {
            View view = mInflater.inflate(R.layout.post_detail_item, parent, false);
            return new PostDetailViewHolder(view);
        } else if (viewType == BaseItem.SECOND_TYPE) {
            View view = mInflater.inflate(R.layout.comment_item, parent, false);
            return new CommentViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);

    }

    @Override
    protected int getCustomItemViewType(int position) {
        Data<BaseItem> baseItem = (Data<BaseItem>) mDataList.get(position);
        return baseItem.attributes.type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostDetailViewHolder) {
            final Data<PostItem> dataTupe = (Data<PostItem>) mDataList.get(position);
            final PostItem item = dataTupe.attributes;
            ((PostDetailViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                }

                @Override
                public void onLove(View view, int position, boolean isLove) {
                    final Data<PostItem> dataTupe = (Data<PostItem>) mDataList.get(position);
                    itemInteractiveListener.onLove(dataTupe.uuid, isLove, position);
                }

                @Override
                public void onComment(View view, int position) {

                }
            });
            ((PostDetailViewHolder) holder).setData(item, mContext);
        } else if (holder instanceof CommentViewHolder) {
            Data<CommentItem> dataTupe = (Data<CommentItem>) mDataList.get(position);
            CommentItem item = dataTupe.attributes;
            ((CommentViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                }

                @Override
                public void onLove(View view, int position, boolean isLove) {

                }

                @Override
                public void onComment(View view, int position) {

                }
            });
            ((CommentViewHolder) holder).setData(item);

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

    public void increaseComment() {
        int comment = ((Data<PostItem>) mDataList.get(0)).attributes.comment + 1;
        ((Data<PostItem>) mDataList.get(0)).attributes.comment = comment;
        notifyItemChanged(0);
    }
}
