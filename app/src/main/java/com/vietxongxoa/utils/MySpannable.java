package com.vietxongxoa.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.vietxongxoa.R;

public class MySpannable extends ClickableSpan {

    private boolean isUnderline = false;
    /**
     * Constructor
     */
    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {

        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#443266"));

    }


}
