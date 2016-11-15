package com.yaoobs.anotherweibo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.activities.BaseActivity;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.LogUtils;
import com.yaoobs.anotherweibo.utils.SPUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private WeiboParameters mWeiboParameters;
    //“GET”, “POST”, “DELETE”
    private String httpMethod;
    private SPUtils mSPUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAsyncWeiboRunner = new AsyncWeiboRunner(getActivity());
        mWeiboParameters = new WeiboParameters(Constant.APP_KEY);
        httpMethod = "GET";
        mSPUtils = SPUtils.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        mAsyncWeiboRunner.requestAsync(Urls.HOME_TIME_LINE, mWeiboParameters, httpMethod, new RequestListener() {
//            @Override
//            public void onComplete(String s) {
//                Log.i("TAG", "onComplete: " + "{" + s + "}");
//                JsonParser parser = new JsonParser();
//                JsonObject object = parser.parse(s).getAsJsonObject();
//                JsonArray array = object.get("statuses").getAsJsonArray();
//                List<StatusEntity> list = new ArrayList<StatusEntity>();
//                Type type = new TypeToken<ArrayList<StatusEntity>>(){}.getType();
//                list = new Gson().fromJson(array,type);
//                Log.i("TAG", "onComplete: "+ list.size()+"");
//            }
//
//            @Override
//            public void onWeiboException(WeiboException e) {
//
//            }
//        });
        new BaseNetWork(getActivity(), Urls.HOME_TIME_LINE) {
            @Override
            public WeiboParameters onPrepare() {
                mWeiboParameters.put(WBConstants.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
                return mWeiboParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean sucess) {
                if (sucess) {
                    LogUtils.d(response.response + "");
//                    Log.i("TAG", "onFinish: " + response.response+"");
                    List<StatusEntity> list = new ArrayList<StatusEntity>();
                    Type type = new TypeToken<ArrayList<StatusEntity>>() {
                    }.getType();
                    list = new Gson().fromJson(response.response, type);
                } else {
                    LogUtils.e(response.message);
                }
            }
        }.get();
        return view;
    }

}
