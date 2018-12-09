package com.vietxongxoa.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vietxongxoa.R;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.BaseModel;
import com.vietxongxoa.model.Comment;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.article.list.ItemInteractiveListener;
import com.vietxongxoa.ui.viewholder.CommentViewHolder;
import com.vietxongxoa.ui.viewholder.LoadMoreRecyclerViewAdapter;
import com.vietxongxoa.ui.viewholder.PostDetailViewHolder;

public class CommentAdapter extends LoadMoreRecyclerViewAdapter<Object> {

    private Context mContext;
    private ItemInteractiveListener itemInteractiveListener;

    public CommentAdapter(
            @NonNull Context context,
            ItemClickListener itemClickListener,
            @NonNull RetryLoadMoreListener retryLoadMoreListener,
            ItemInteractiveListener itemInteractiveListener
    ) {
        super(context, itemClickListener, retryLoadMoreListener);
        this.itemInteractiveListener = itemInteractiveListener;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseModel.HEADER_TYPE) {
            View view = mInflater.inflate(R.layout.item_article_detail, parent, false);
            return new PostDetailViewHolder(view);
        } else if (viewType == BaseModel.SECOND_TYPE) {
            View view = mInflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);

    }

    @Override
    protected int getCustomItemViewType(int position) {
        Data<BaseModel> baseItem = (Data<BaseModel>) mDataList.get(position);
        return baseItem.attributes.type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostDetailViewHolder) {
            final Data<Article> dataTupe = (Data<Article>) mDataList.get(position);
            final Article item = dataTupe.attributes;
            ((PostDetailViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                }

                @Override
                public void onLove(View view, int position, boolean isLove) {
                    final Data<Article> dataTupe = (Data<Article>) mDataList.get(position);
                    itemInteractiveListener.onLove(dataTupe.uuid, isLove, position);
                }

                @Override
                public void onComment(View view, int position) {

                }
            });
            ((PostDetailViewHolder) holder).setData(item, mContext);
        } else if (holder instanceof CommentViewHolder) {
            Data<Comment> dataTupe = (Data<Comment>) mDataList.get(position);
            Comment item = dataTupe.attributes;
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
        ((Data<Article>) mDataList.get(pos)).attributes.loved = true;
        int love = Integer.parseInt(((Data<Article>) mDataList.get(pos)).attributes.love) + 1;
        ((Data<Article>) mDataList.get(pos)).attributes.love = String.valueOf(love);
        notifyItemChanged(pos);
    }

    public void unLove(int pos) {
        ((Data<Article>) mDataList.get(pos)).attributes.loved = false;
        int love = Integer.parseInt(((Data<Article>) mDataList.get(pos)).attributes.love) - 1;
        ((Data<Article>) mDataList.get(pos)).attributes.love = String.valueOf(love);
        notifyItemChanged(pos);
    }

    public void increaseComment() {
        int comment = ((Data<Article>) mDataList.get(0)).attributes.comment + 1;
        ((Data<Article>) mDataList.get(0)).attributes.comment = comment;
        notifyItemChanged(0);
    }
}
