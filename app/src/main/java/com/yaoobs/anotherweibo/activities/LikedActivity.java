package com.yaoobs.anotherweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yaoobs.anotherweibo.R;

public class LikedActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_like);
    }

    public int getLayoutId() {
        return R.layout.activity_liked;
    }
}
