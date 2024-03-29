package com.yaoobs.anotherweibo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by yaoobs on 2016/11/15.
 */

public class SPUtils {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SPUtils instance;
    private static final String SP_NAME = "AnotherWeibo";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String IS_LOGIN = "IS_LOGIN";

    private SPUtils() {

    }

    public static SPUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtils.class) {
                instance = new SPUtils();
                mSharedPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
                mEditor = mSharedPreferences.edit();
            }
        }
        return instance;
    }

    public void saveToken(Oauth2AccessToken accessToken) {
        mEditor.putString(ACCESS_TOKEN, new Gson().toJson(accessToken)).commit();
        mEditor.putBoolean(IS_LOGIN, true).commit();
    }
    public  Oauth2AccessToken  getToken(){
        String json = mSharedPreferences.getString(ACCESS_TOKEN,"");
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return new Gson().fromJson(json,Oauth2AccessToken.class);
    }

    public boolean isLogin() {
        return mSharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logOut(){
        mEditor.remove(ACCESS_TOKEN);
        mEditor.remove(IS_LOGIN);
        mEditor.commit();
    }
}
