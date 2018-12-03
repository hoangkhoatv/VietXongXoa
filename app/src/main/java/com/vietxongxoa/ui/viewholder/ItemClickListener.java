package com.vietxongxoa.ui.viewholder;

import android.view.View;

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
    void onLove(View view, int position, boolean isLove);
    void onComment(View view, int position);
}
