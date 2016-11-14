package com.yaoobs.anotherweibo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yaoobs.anotherweibo.R;

public class FristActivity extends BaseActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FristActivity.this,SecondActivity.class));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_frist;
    }
}
