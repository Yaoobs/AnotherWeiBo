package com.yaoobs.anotherweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.CommentEntity;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.LogUtils;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.mvpviews.CommentView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public class CommentPresenterImp implements BasePresenter{
    private CommentView mView;
    private SPUtils mSPUtils;
    private WeiboParameters mParameters;
    private int page = 1;
    private List<CommentEntity> mEntityList;

    public CommentPresenterImp(CommentView view) {
        mView = view;
        mSPUtils = SPUtils.getInstance(mView.getActivity());
        mParameters = new WeiboParameters(Constant.APP_KEY);
        mEntityList = new ArrayList<>();
    }

    @Override
    public void loadData(boolean showLoading) {
        page = 1;
        loadData(false, showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        page++;
        loadData(true, showLoading);

    }

    private void loadData(final boolean loadMore, boolean showLoading) {

        new BaseNetWork(mView, Urls.COMMENTS_BY_ME, showLoading) {
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils
                        .getToken().getToken());
                mParameters.put(ParameterKeySet.PAGE, page);
                mParameters.put(ParameterKeySet.COUNT, 10);
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    LogUtils.e(response.response);
                    Type type = new TypeToken<ArrayList<CommentEntity>>() {
                    }.getType();
                    List<CommentEntity> list = new Gson().fromJson(response.response, type);
                    if (!loadMore) {
                        mEntityList.clear();
                    }
                    mEntityList.addAll(list);
                    mView.onSuccess(mEntityList);
                }
            }
        }.get();

    }
}
