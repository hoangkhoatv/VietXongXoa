package com.vietxongxoa.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.utils.IconTextView;

public class PostDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textPost;
    private TextView textDate;
    private ItemClickListener itemClickListener;
    private IconTextView iconLove;
    private Button btnLove;
    private TextView numLoved;
    private TextView numComment;
    private Context context;

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
                itemClickListener.onLove(itemView, getAdapterPosition(), iconLove.isLove());
            }
        });


    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void setData(Object item, Context context) {
        this.context = context;
        PostItem postItem = (PostItem) item;
        String userName = postItem.author;
        String strPost = postItem.content;
        String strDate = postItem.created;
        textName.setText(userName);
        textDate.setText(strDate);
        numLoved.setText(postItem.love);
        numComment.setText(String.valueOf(postItem.comment));

        if (postItem.loved) {
            numLoved.setTextColor(context.getResources().getColor(R.color.heart));
            iconLove.setLove();
        } else {
            numLoved.setTextColor(context.getResources().getColor(R.color.comment));
            iconLove.setNotLove();
        }

        textPost.setText(strPost);

    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}