package com.yaoobs.anotherweibo.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.utils.SPUtils;

public class LandingPageActivity extends BaseActivity {
    private SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;
    private SPUtils mSPUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();

        mSPUtils = SPUtils.getInstance(getApplicationContext());
        mAuthInfo = new AuthInfo(getApplicationContext(), Constant.APP_KEY, Constant.REDIRECT_URL, Constant
                .SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             checkLogin();
            }
        }, 500);
    }

    private void checkLogin() {
        if (mSPUtils.isLogin()) {
            startActivity(new Intent(LandingPageActivity.this, HomePageActivity.class));
            finish();
        } else {

            mSsoHandler.authorize(new WeiboAuthListener() {
                @Override
                public void onComplete(Bundle bundle) {
                    Log.i("TAG", "onComplete: " + bundle + "");
                    Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(bundle);
                    mSPUtils.saveToken(token);
                    startActivity(new Intent(LandingPageActivity.this, HomePageActivity.class));
                    finish();
                }

                @Override
                public void onWeiboException(WeiboException e) {

                }

                @Override
                public void onCancel() {

                }
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_landing_page;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mSsoHandler) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
