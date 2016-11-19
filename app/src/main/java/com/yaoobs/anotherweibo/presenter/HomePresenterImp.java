package com.yaoobs.anotherweibo.presenter;

import android.widget.ListAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.adapters.HomepageListAdapter;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.mvpviews.HomeView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoobs on 2016/11/19.
 */

public class HomePresenterImp implements HomePresenter {
    private String url = Urls.HOME_TIME_LINE;
    private int page = 1;
    private List<StatusEntity> mEntityList;
    WeiboParameters mParameters;
    private SPUtils mSPUtils;
    private HomeView mHomeView;
    private HomepageListAdapter mListAdapter;

    public HomePresenterImp(HomeView mHomeView) {
        this.mHomeView = mHomeView;
        mEntityList = new ArrayList<>();
        mSPUtils = SPUtils.getInstance(mHomeView.getActivity());
        mParameters = new WeiboParameters(Constant.APP_KEY);
//        mListAdapter = new HomepageListAdapter(mEntityList, mHomeView.getActivity());
    }

    @Override
    public void loadData() {
        page = 1;
        loadData(false);
    }

    @Override
    public void loadMore() {
        page++;
        loadData(true);
    }

    @Override
    public void requestHomeTimeLine() {
        url = Urls.HOME_TIME_LINE;
        loadData(false);
    }

    @Override
    public void requestUserTimeLine() {
        url = Urls.USER_TIME_LINE;
        loadData(false);
    }

//    @Override
//    public HomepageListAdapter getAdapter() {
//        return mListAdapter;
//    }

    private void loadData(final boolean loadMore) {
        new BaseNetWork(mHomeView.getActivity(), url) {
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.PAGE, page);
                mParameters.put(ParameterKeySet.COUNT, 10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean sucess) {
                if (sucess) {
//                    Log.i("TAG", "onFinish: " + response.response+"");
                    List<StatusEntity> list = new ArrayList<StatusEntity>();
                    Type type = new TypeToken<ArrayList<StatusEntity>>() {
                    }.getType();
                    list = new Gson().fromJson(response.response, type);
                    if (!loadMore) {
                        mEntityList.clear();
                    }
                    mEntityList.addAll(list);
                    mHomeView.onSuccess(mEntityList);
//                    mListAdapter.notifyDataSetChanged();
                } else {
                    mHomeView.onError(response.message);
                }
            }
        }.get();
    }
}
