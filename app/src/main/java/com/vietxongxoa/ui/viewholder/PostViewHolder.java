package com.vietxongxoa.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vietxongxoa.R;
import com.vietxongxoa.model.BaseItem;
import com.vietxongxoa.model.PostItem;
import com.vietxongxoa.utils.MySpannable;
import com.vietxongxoa.utils.TextUtils;

import org.w3c.dom.Text;

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
        final String strPost= postItem.content;
        String strDate = postItem.created;
        textName.setText(userName);
        textDate.setText(strDate);
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