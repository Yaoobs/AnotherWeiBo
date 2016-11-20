package com.yaoobs.anotherweibo.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaoobs.anotherweibo.R;

/**
 * Created by yaoobs on 2016/11/20.
 */

public class LoadingView {
    private RelativeLayout mRelativeLayout;
    private View mView;
    private LayoutInflater mInflater;
    private ImageView ivLoading;
    private TextView tvLoading;
    private Context mContext;
    private String mTag = "LoaidngView";

    public LoadingView(RelativeLayout relativeLayout) {
        mRelativeLayout = relativeLayout;
        mContext = relativeLayout.getContext();
        mInflater = LayoutInflater.from(mContext);
        mView = mInflater.inflate(R.layout.layout_loading, null);
        mView.setTag(mTag);
        if (mRelativeLayout.findViewWithTag(mTag) != null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRelativeLayout.addView(mView, layoutParams);
        initialize(mView);
        mView.setVisibility(View.GONE);
    }

    private void initialize(View view) {

        ivLoading = (ImageView) view.findViewById(R.id.ivLoading);
        tvLoading = (TextView) view.findViewById(R.id.tvLoading);
    }

    public void show() {
        if (null != mView) {
            mView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.bg_loading).asGif().into(ivLoading);
        }
    }

    public void hide() {
        if (null != mView) {
            mView.setVisibility(View.GONE);
        }
    }
}
