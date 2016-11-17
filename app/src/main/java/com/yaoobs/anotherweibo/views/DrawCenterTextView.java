package com.yaoobs.anotherweibo.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by yaoobs on 2016/11/17.
 */

public class DrawCenterTextView extends TextView {
    public DrawCenterTextView(Context context) {
        super(context);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawables[] = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if (null != drawableLeft) {
            setGravity(Gravity.START);
            int drawWidth = drawableLeft.getIntrinsicWidth();
            int drawablePadding= getCompoundDrawablePadding();
            int textWidth = (int) getPaint().measureText(getText().toString());
            int bodyWidth = drawWidth + textWidth+drawablePadding;
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
        Drawable drawableRight = drawables[2];
        if (null != drawableRight) {
            setGravity(Gravity.END);
            int drawWidth = drawableRight.getIntrinsicWidth();
            int drawablePadding= getCompoundDrawablePadding();
            int textWidth = (int) getPaint().measureText(getText().toString());
            int bodyWidth = drawWidth + textWidth+drawablePadding;
            canvas.translate(-(getWidth() - bodyWidth)/ 2, 0);
        }
        super.onDraw(canvas);
    }
}
