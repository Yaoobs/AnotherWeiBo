package com.yaoobs.anotherweibo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoobs.anotherweibo.R;

public abstract class BaseActivity extends AppCompatActivity {

    private RelativeLayout rlContent;
    private TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_base);
        initialize();
        View v = getLayoutInflater().inflate(getLayoutId(),rlContent,false);
        rlContent.addView(v);

    }

    public abstract int getLayoutId();

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
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    private void initialize() {
        txt = (TextView) findViewById(R.id.txt);
        rlContent = (RelativeLayout) findViewById(R.id.rlContent);
    }
}
