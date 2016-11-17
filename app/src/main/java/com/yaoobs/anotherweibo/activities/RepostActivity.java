package com.yaoobs.anotherweibo.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.RichTextUtils;
import com.yaoobs.anotherweibo.utils.SPUtils;

import de.greenrobot.event.EventBus;

public class RepostActivity extends BaseActivity {

    private EditText etContent;
    private long id;
    private String content;
    private String action;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra(ParameterKeySet.ID, 0);
        action = getIntent().getAction();
        switch (action) {
            case "COMMENT":
                getToolbar().setTitle(R.string.lbl_comment);
                url = Urls.COMMENT_CREATE;
                break;
            case "REPOST":
                getToolbar().setTitle(R.string.lbl_repost);
                content = getIntent().getStringExtra(ParameterKeySet.STATUS);
                url =Urls.STATUS_REPOST;
                break;
        }
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repost;
    }

    private void initialize() {
        etContent = (EditText) findViewById(R.id.etContent);
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(RichTextUtils.getRichText(getApplicationContext(),"//"+ content));

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repost, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        post(etContent.getText().toString());
        return true;
    }

    private void post(final String string) {
        new BaseNetWork(getApplicationContext(),url) {
            public WeiboParameters onPrepare() {
                WeiboParameters weiboParameters = new WeiboParameters(Constant.APP_KEY);
                weiboParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, SPUtils.getInstance(getApplicationContext())
                        .getToken().getToken());
                if(action.equals("REPOST")){
                    weiboParameters.put(ParameterKeySet.STATUS,string);

                }
                else {
                    weiboParameters.put(ParameterKeySet.COMMENT,  string);

                }
                weiboParameters.put(ParameterKeySet.ID, id);
                return weiboParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    EventBus.getDefault().post("onFinish");
                    finish();
                }
            }
        }.post();
    }
}
