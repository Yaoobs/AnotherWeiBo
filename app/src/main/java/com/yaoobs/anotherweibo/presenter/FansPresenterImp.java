package com.yaoobs.anotherweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.UserEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.LogUtils;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.mvpviews.FansView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public class FansPresenterImp implements FansPresenter {
    private FansView mFansView;
    private List<UserEntity> mList;
    private SPUtils mSPUtils;
    private WeiboParameters mParameters;
    private String url;
    private int page =0;

    public FansPresenterImp(FansView fansView) {
        mFansView = fansView;
        mSPUtils = SPUtils.getInstance(mFansView.getActivity());
        mList = new ArrayList<>();
        mParameters = new WeiboParameters(Constant.APP_KEY);
        url = Urls.FRIENDS;
    }

    public void requestMyFans() {
        url = Urls.FOLLOWERS;
    }

    public void requestMyAttentions() {
        url = Urls.FRIENDS;

    }

    public void loadData(boolean showLoading) {
        page =0;
        loadData(false,showLoading);
    }

    public void loadMore(boolean showLoading) {
        page++;
        loadData(true,false);
    }
    private void loadData(final boolean loadMore,boolean showLoading){
        new BaseNetWork(mFansView, url,showLoading) {
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.GAME_PARAMS_UID, mSPUtils.getToken().getUid());
                mParameters.put(ParameterKeySet.CURSOR, page);
                mParameters.put(ParameterKeySet.COUNT, 10);
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                LogUtils.e(response.response);
                if (success) {
                    Type type = new TypeToken<ArrayList<UserEntity>>() {
                    }.getType();
                    List<UserEntity> list = new Gson().fromJson(response.response, type);
                    if (!loadMore) {
                        mList.clear();
                    }
                    mList.addAll(list);
                    mFansView.onSuccess(mList);
                }
            }
        }.get();

    }
}
