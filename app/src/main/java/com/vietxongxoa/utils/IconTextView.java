package com.vietxongxoa.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import com.vietxongxoa.R;


public class IconTextView extends android.support.v7.widget.AppCompatTextView {
    private Context context;

    public IconTextView(Context context) {
        super(context);
        this.context = context;
        createView();
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    private void createView() {
        setGravity(Gravity.CENTER);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "awesome.otf");
        setTypeface(font);
    }

    public void setNotLove() {
        setTextColor(context.getResources().getColor(R.color.comment));
    }

    public void setLove() {
        setTextColor(context.getResources().getColor(R.color.heart));
    }

    public boolean isLove() {
        return getCurrentTextColor() == context.getResources().getColor(R.color.heart);
    }
}
