package com.vietxongxoa.utils;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class MySpannable extends ClickableSpan {

    private boolean isUnderline = false;

    /**
     * Constructor
     */
    protected MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void onClick(@NonNull View view) {

    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#443266"));
    }
}
