package com.yaoobs.anotherweibo.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.adapters.HomepageListAdapter;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.DividerItemDecoration;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.yaoobs.anotherweibo.networks.Urls.USER_TIME_LINE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private WeiboParameters mParameters;
    //“GET”, “POST”, “DELETE”
    private String httpMethod;
    private SPUtils mSPUtils;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<StatusEntity> mEntityList;
    private HomepageListAdapter mListAdapter;
    private int page = 1;
    private String url = Urls.HOME_TIME_LINE;;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAsyncWeiboRunner = new AsyncWeiboRunner(getActivity());
        mParameters = new WeiboParameters(Constant.APP_KEY);
        httpMethod = "GET";
        mSPUtils = SPUtils.getInstance(getActivity());
        mEntityList = new ArrayList<>();
        mListAdapter = new HomepageListAdapter(mEntityList, getActivity());
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rlv = (PullToRefreshRecyclerView) inflater.inflate(R.layout.v_common_recyclerview, container, false);
        init();
        loadData(Urls.HOME_TIME_LINE, false);

        return rlv;
    }

    private void loadData(String url, final boolean loadMore) {
        new BaseNetWork(getActivity(), url) {
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
                    rlv.onRefreshComplete();
                    mEntityList.addAll(list);
                    mListAdapter.notifyDataSetChanged();

                } else {
                }
            }
        }.get();
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rlv.getRefreshableView().setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        rlv.getRefreshableView().addItemDecoration(mItemDecoration);
        rlv.getRefreshableView().setAdapter(mListAdapter);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                page = 1;
                loadData(url, false);
            }

            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                page++;
                loadData(url, true);
            }
        });
    }

    public void onEventMainThread(Object event) {
        if (event instanceof Integer) {
            int id = (int) event;
            switch (id) {
                case R.id.action_one:
                    url = Urls.HOME_TIME_LINE;
                    break;
                case R.id.action_two:
                    url = Urls.USER_TIME_LINE;
                    break;
            }
            loadData(url,false);
        }
        if (event instanceof String) {
            loadData(url,false);
        }

    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
