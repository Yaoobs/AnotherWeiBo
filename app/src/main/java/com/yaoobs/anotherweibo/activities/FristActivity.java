package com.yaoobs.anotherweibo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.ToolbarX;

public class FristActivity extends BaseActivity {
    private Button btn;
    private ToolbarX mToolbarX;
    private RadioGroup custom_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FristActivity.this, SecondActivity.class));
            }
        });
        getToolbar().setDisplayHomeAsUpEnabled(true).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setCustomView(custom_view).setTitle("Toolbar").setSubTitle("XSub");
//        mToolbarX.setTitle(R.string.title_first);
//        mToolbarX.setSubTitle(R.string.title_sub_first);


    }

    private void initialize() {
        btn = (Button) findViewById(R.id.button);
        custom_view = (RadioGroup) getLayoutInflater().inflate(R.layout.view_custom,null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(FristActivity.this, "ss", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_frist;
    }
}
