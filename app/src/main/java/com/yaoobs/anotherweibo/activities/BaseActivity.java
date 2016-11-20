package com.yaoobs.anotherweibo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.views.LoadingView;
import com.yaoobs.anotherweibo.views.ToolbarX;
import com.yaoobs.anotherweibo.views.mvpviews.BaseView;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    private RelativeLayout rlContent;
    private Toolbar toolbar;
    private ToolbarX mToolbarX;
    private LoadingView mLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initialize();
        View v= getLayoutInflater().inflate(getLayoutId(), rlContent, false);
        rlContent.addView(v);
        mToolbarX = new ToolbarX(toolbar,this);
        mLoadingView = new LoadingView(rlContent);
    }
    public  abstract  int getLayoutId();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right,R.anim.anim_out_left_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
    }

    private void initialize() {

        rlContent = (RelativeLayout) findViewById(R.id.rlContent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
    }
    public  ToolbarX getToolbar(){
        if(null==mToolbarX){
            mToolbarX = new ToolbarX(toolbar,this);
        }
        return mToolbarX;
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hide();
    }
}
