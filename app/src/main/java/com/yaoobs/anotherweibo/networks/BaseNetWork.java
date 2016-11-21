package com.yaoobs.anotherweibo.networks;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.views.mvpviews.BaseView;

/**
 * Created by yaoobs on 2016/11/15.
 */

public abstract class BaseNetWork {
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private String url;
    private BaseView mBaseView;
    private boolean mShowLoading;

    public BaseNetWork(BaseView baseView, String url) {
        this.url = url;
        mBaseView = baseView;
        mAsyncWeiboRunner = new AsyncWeiboRunner(mBaseView.getActivity());
    }

    public BaseNetWork(BaseView baseView, String url, boolean showLoading) {
        mBaseView = baseView;
        mAsyncWeiboRunner = new AsyncWeiboRunner(mBaseView.getActivity());
        this.url = url;
        mShowLoading = showLoading;
    }

    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            if (mShowLoading) {
                mBaseView.hideLoading();
            }
            boolean success = false;
            HttpResponse response = new HttpResponse();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("error_code")) {
                    response.code = object.get("error_code").getAsInt();
                }
                if (object.has("error")) {
                    response.message = object.get("error").getAsString();
                    mBaseView.onError(response.message);
                    onFinish(response, false);
                }
                if (object.has("statuses")) {
                    response.response = object.get("statuses").toString();
                    success = true;
                } else if (object.has("users")) {
                    response.response = object.get("users").toString();
                    success = true;

                } else if (object.has("comments")) {
                    response.response = object.get("comments").toString();
                    success = true;
                }else if(object.has("favorites")){
                    response.response = object.get("favorites").toString();
                    success = true;
                } else {
                    response.response = s;
                    success = true;
                }
            }
            onFinish(response, success);
        }

        @Override
        public void onWeiboException(WeiboException e) {
            HttpResponse response = new HttpResponse();
            response.message = e.getMessage();
            mBaseView.onError(response.message);
            onFinish(response, false);
        }
    };

    public void get() {
        request("GET");
    }

    public void post() {
        request("POST");
    }

    public void delete() {
        request("DELETE");

    }

    private void request(String method) {
        if (mShowLoading) {
            mBaseView.showLoading();
        }
        mAsyncWeiboRunner.requestAsync(url, onPrepare(), method, mRequestListener);

    }

    public abstract WeiboParameters onPrepare();

    public abstract void onFinish(HttpResponse response, boolean success);
}
