package com.vietxongxoa.ui.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.utils.IconTextView;
import com.vietxongxoa.utils.MySpannable;

import butterknife.BindView;
import butterknife.OnClick;

public class PostViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textName;
    private TextView textPost;
    private TextView textDate;
    private ItemClickListener itemClickListener;
    private IconTextView iconLove;
    private Button btnLove;
    private Button btnComment;
    private TextView numLoved;
    private Context context;

    public PostViewHolder(final View itemView) {
        super(itemView);
        textName= (TextView) itemView.findViewById(R.id.text_name);
        textPost = (TextView) itemView.findViewById(R.id.text_post);
        textDate = (TextView) itemView.findViewById(R.id.text_date);
        btnLove = (Button) itemView.findViewById(R.id.btnLove);
        btnComment = (Button) itemView.findViewById(R.id.btnComment);
        iconLove = (IconTextView) itemView.findViewById(R.id.iconLove);
        numLoved = (TextView) itemView.findViewById(R.id.numLoved);
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
        this.context = context;
        PostItem postItem= (PostItem) item;
        String userName = postItem.author;
        final String strPost= postItem.content;
        String strDate = postItem.created;
        textName.setText(userName);
        textDate.setText(strDate);
        numLoved.setText(postItem.love);

        if (postItem.loved){
            numLoved.setTextColor(context.getResources().getColor(R.color.heart));
            iconLove.setLove();
        } else {
            numLoved.setTextColor(context.getResources().getColor(R.color.comment));
            iconLove.setNotLove();
        }

        if ( strPost.length() >280){
            String temp = strPost.substring(0,280);
            String viewMore = temp + "...xem thêm";
            Spannable span = SpannableStringBuilder.valueOf(viewMore);
            span.setSpan(new MySpannable(false){
                @Override
                public void onClick(View view) {
                    textPost.setText(strPost);
                }
            }, viewMore.indexOf("...xem thêm"), viewMore.indexOf("...xem thêm")+"...xem thêm".length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textPost.setText(span);
            textPost.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            textPost.setText(strPost);
        }
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}