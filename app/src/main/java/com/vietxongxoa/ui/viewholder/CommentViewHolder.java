package com.vietxongxoa.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.Comment;

public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textComment;
    private ItemClickListener itemClickListener;
    private TextView textDate;

    public CommentViewHolder(View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.text_name);
        textComment = itemView.findViewById(R.id.text_comment);
        TextView numLoved = itemView.findViewById(R.id.numLoved);
        textDate = itemView.findViewById(R.id.text_date);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(Object item) {
        Comment comment = (Comment) item;
        textName.setText(comment.author);
        textComment.setText(comment.content);
        textDate.setText(comment.created);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
