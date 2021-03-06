package com.vietxongxoa.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.utils.IconTextView;

public class PostDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textPost;
    private TextView textDate;
    private ItemClickListener itemClickListener;
    private IconTextView iconLove;
    private TextView numLoved;
    private TextView numComment;
    private Button btnLove;
    public PostDetailViewHolder(final View itemView) {
        super(itemView);
        textName = (TextView) itemView.findViewById(R.id.text_name);
        textPost = (TextView) itemView.findViewById(R.id.text_post);
        textDate = (TextView) itemView.findViewById(R.id.text_date);
        btnLove = (Button) itemView.findViewById(R.id.btnLove);
        iconLove = (IconTextView) itemView.findViewById(R.id.iconLove);
        numLoved = (TextView) itemView.findViewById(R.id.numLoved);
        numComment = (TextView) itemView.findViewById(R.id.numberComment);
        itemView.setOnClickListener(this);

        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLove.setEnabled(false);
                itemClickListener.onLove(itemView, getAdapterPosition(), iconLove.isLove());
            }
        });
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void setData(Object item, Context context) {
        Article article = (Article) item;
        String userName = article.author;
        String strPost = article.content;
        String strDate = article.created;
        textName.setText(userName);
        textDate.setText(strDate);
        numLoved.setText(article.love);
        numComment.setText(String.valueOf(article.comment));
        if (article.loved) {
            numLoved.setTextColor(context.getResources().getColor(R.color.heart));
            iconLove.setLove();
        } else {
            numLoved.setTextColor(context.getResources().getColor(R.color.comment));
            iconLove.setNotLove();
        }
        textPost.setText(strPost);
        btnLove.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
