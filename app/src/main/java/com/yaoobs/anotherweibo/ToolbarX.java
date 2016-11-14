package com.yaoobs.anotherweibo;

import android.app.PendingIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by yaoobs on 2016/11/14.
 */

public class ToolbarX {
    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;
    private RelativeLayout rlCustom;

    public ToolbarX(Toolbar toolbar, AppCompatActivity activity) {
        mToolbar = toolbar;
        rlCustom = (RelativeLayout) mToolbar.findViewById(R.id.rlCustom);
        mActivity = activity;
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    public ToolbarX setTitle(String text){
        return this;
    }

    public ToolbarX setSubTitle(String text){
        return this;
    }

    public ToolbarX setTitle(int resId){
        return this;
    }

    public ToolbarX setSubTitle(int resId){
        return this;
    }

    public ToolbarX setNavigationOnClickListener(View.OnClickListener listener){
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    public ToolbarX setNavigationIcon(int resId){
        mToolbar.setNavigationIcon(resId);
        return this;
    }

    public ToolbarX setDisplayHomeAsUpEnabled(boolean show) {
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    public ToolbarX setCustomView(View view){
        rlCustom.removeAllViews();
        rlCustom.addView(view);
        return this;
    }
}
