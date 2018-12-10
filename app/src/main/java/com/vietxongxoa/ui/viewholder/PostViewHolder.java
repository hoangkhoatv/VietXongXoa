package com.vietxongxoa.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.Article;
import com.vietxongxoa.utils.IconTextView;
import com.vietxongxoa.utils.MySpannable;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textPost;
    private TextView textDate;
    private TextView commentNumber;
    private ItemClickListener itemClickListener;
    private IconTextView iconLove;
    private TextView numLoved;

    public PostViewHolder(final View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.text_name);
        textPost = itemView.findViewById(R.id.text_post);
        textDate = itemView.findViewById(R.id.text_date);
        Button btnLove = itemView.findViewById(R.id.btnLove);
        Button btnComment = itemView.findViewById(R.id.btnComment);
        iconLove = itemView.findViewById(R.id.iconLove);
        numLoved = itemView.findViewById(R.id.numLoved);
        commentNumber =  itemView.findViewById(R.id.commentNumber);
        itemView.setOnClickListener(this);
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onLove(itemView, getAdapterPosition(), iconLove.isLove());
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onComment(itemView, getAdapterPosition());
            }
        });
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void setData(Object item, Context context) {
        Article article = (Article) item;
        String userName = article.author;
        final String strPost = article.content;
        String strDate = article.created;
        textName.setText(userName);
        textDate.setText(strDate);
        numLoved.setText(article.love);
        commentNumber.setText(String.valueOf(article.comment));
        if (article.loved) {
            numLoved.setTextColor(context.getResources().getColor(R.color.heart));
            iconLove.setLove();
        } else {
            numLoved.setTextColor(context.getResources().getColor(R.color.comment));
            iconLove.setNotLove();
        }
        if (strPost.length() > 280) {
            String temp = strPost.substring(0, 280);
            String readMoreString = context.getString(R.string.read_more);
            String viewMore = temp + readMoreString;
            Spannable span = SpannableStringBuilder.valueOf(viewMore);
            span.setSpan(
                    new MySpannable(false) {
                        @Override
                        public void onClick(@NonNull View view) {
                            textPost.setText(strPost);
                        }
                    },
                    viewMore.indexOf(readMoreString),
                    viewMore.indexOf(readMoreString) + readMoreString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            textPost.setText(span);
            textPost.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            textPost.setText(strPost);
        }
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
