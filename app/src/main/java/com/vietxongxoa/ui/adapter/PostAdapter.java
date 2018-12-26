package com.vietxongxoa.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.vietxongxoa.R;
import com.vietxongxoa.data.local.PreferencesHelper;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.model.BaseModel;
import com.vietxongxoa.model.Data;
import com.vietxongxoa.ui.article.create.ArticleCreateActivity;
import com.vietxongxoa.ui.article.detail.ArticleDetailActivity;
import com.vietxongxoa.ui.article.list.ArticleListActivity;
import com.vietxongxoa.ui.article.list.ItemInteractiveListener;
import com.vietxongxoa.ui.viewholder.LoadMoreRecyclerViewAdapter;
import com.vietxongxoa.ui.viewholder.PostViewHolder;
import com.vietxongxoa.ui.viewholder.WriteViewHolder;


public class PostAdapter extends LoadMoreRecyclerViewAdapter<Object> {

    private Context mContext;
    private ItemInteractiveListener itemInteractiveListener;

    public PostAdapter(
            Context context,
            ItemClickListener itemClickListener,
            RetryLoadMoreListener retryLoadMoreListener,
            ItemInteractiveListener itemInteractiveListener
    ) {
        super(context, itemClickListener, retryLoadMoreListener);
        mContext = context;
        this.itemInteractiveListener = itemInteractiveListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseModel.HEADER_TYPE) {
            View view = mInflater.inflate(R.layout.item_write_new_post, parent, false);
            return new WriteViewHolder(view);
        } else if (viewType == BaseModel.SECOND_TYPE) {
            View view = mInflater.inflate(R.layout.item_article_in_list, parent, false);
            return new PostViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected int getCustomItemViewType(int position) {
        Data<BaseModel> baseItem = (Data<BaseModel>) mDataList.get(position);
        return baseItem.attributes.type;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof PostViewHolder) {
            final Data<Article> data = (Data<Article>) mDataList.get(position);
            final Article item = data.attributes;
            ((PostViewHolder) holder).setItemClickListener(
                    new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent intent = ArticleDetailActivity.getStartIntent(mContext);
                            intent.putExtra(PreferencesHelper.KEY_ARTICLE_UUID, data.uuid);
                            intent.putExtra(PreferencesHelper.KEY_AUTHOR, item.author);
                            intent.putExtra(PreferencesHelper.KEY_CONTENT, item.content);
                            intent.putExtra(PreferencesHelper.KEY_DATE, item.created);
                            intent.putExtra(PreferencesHelper.KEY_NUM_LOVE, item.love);
                            intent.putExtra(PreferencesHelper.KEY_COMMENT, item.comment);
                            intent.putExtra(PreferencesHelper.KEY_LOVED, item.loved);
                            intent.putExtra(PreferencesHelper.KEY_POSITON,position);
                            ((ArticleListActivity) mContext).startActivityForResult(intent,ArticleListActivity.UPDATE_ITEM);
                        }

                        @Override
                        public void onLove(View view, int pos, boolean isLove) {
                            final Data<Article> data = (Data<Article>) mDataList.get(pos);
                            itemInteractiveListener.onLove(data.uuid, isLove, position);
                        }

                        @Override
                        public void onComment(View view, int position) {

                        }
                    }
            );
            ((PostViewHolder) holder).setData(item, mContext);
            ((PostViewHolder) holder).textPost.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ((PostViewHolder) holder).textPost.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int lineCount = ((PostViewHolder) holder).textPost.getLineCount();
                    if (lineCount > 5){
                        for (int i = 0 ; i < 5; i++){
                            Log.d("BBBBB", String.valueOf(((PostViewHolder) holder).textPost.getLayout().getLineStart(i)));
                        }
                    }

                }
            });
        } else if (holder instanceof WriteViewHolder) {
            ((WriteViewHolder) holder).setItemClickListener(new com.vietxongxoa.ui.viewholder.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    mContext.startActivity(ArticleCreateActivity.getStartIntent(mContext));
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

    public void changeItem(int position, Article article) {
        ((Data<Article>) mDataList.get(position)).attributes.loved = article.loved;
        ((Data<Article>) mDataList.get(position)).attributes.comment = article.comment;
        ((Data<Article>) mDataList.get(position)).attributes.love = article.love;
        notifyItemChanged(position);


    }


}
