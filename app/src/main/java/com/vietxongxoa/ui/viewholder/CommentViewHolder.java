package com.vietxongxoa.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.CommentItem;

public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textComment;
    private ItemClickListener itemClickListener;
    private TextView textDate;

    public CommentViewHolder(View itemView) {
        super(itemView);
        textName = (TextView) itemView.findViewById(R.id.text_name);
        textComment = (TextView) itemView.findViewById(R.id.text_comment);
        TextView numLoved = (TextView) itemView.findViewById(R.id.numLoved);
        textDate = (TextView) itemView.findViewById(R.id.text_date);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(Object item) {
        CommentItem commentItem = (CommentItem) item;
        textName.setText(commentItem.author);
        textComment.setText(commentItem.content);
        textDate.setText(commentItem.created);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
