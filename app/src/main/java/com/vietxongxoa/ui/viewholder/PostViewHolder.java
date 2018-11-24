package com.vietxongxoa.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.PostItem;

public class PostViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textPost;
    private TextView textDate;
    private ItemClickListener itemClickListener;

    public PostViewHolder(View itemView) {
        super(itemView);
        textName= (TextView) itemView.findViewById(R.id.text_name);
        textPost = (TextView) itemView.findViewById(R.id.text_post);
        textDate = (TextView) itemView.findViewById(R.id.text_date);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(Object item) {
        PostItem postItem= (PostItem) item;
        String userName = postItem.author;
        String strPost= postItem.content;
        String strDate = postItem.created;
        textName.setText(userName);
        textPost.setText(strPost);
        textDate.setText(strDate);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}