package com.yaoobs.anotherweibo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.constant.WBConstants;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private WeiboParameters mParameters;
    //“GET”, “POST”, “DELETE”
    private String httpMethod;
    private SPUtils mSPUtils;
    private RecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<StatusEntity> mEntityList;
    private HomepageListAdapter mListAdapter;
    private int page =1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAsyncWeiboRunner = new AsyncWeiboRunner(getActivity());
        mParameters = new WeiboParameters(Constant.APP_KEY);
        httpMethod = "GET";
        mSPUtils = SPUtils.getInstance(getActivity());
        mEntityList = new ArrayList<>();
        mListAdapter = new HomepageListAdapter(mEntityList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rlv = (RecyclerView) inflater.inflate(R.layout.v_common_recyclerview, container, false);
        init();
        new BaseNetWork(getActivity(), Urls.HOME_TIME_LINE) {
            @Override
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.PAGE, page);
                mParameters.put(ParameterKeySet.COUNT, 1);
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
                    if (null != list && list.size() > 0) {
                        mEntityList.clear();
                        mEntityList.addAll(list);
                    }
                    mListAdapter.notifyDataSetChanged();
                } else {
                }
            }
        }.get();
        return rlv;
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rlv.setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        rlv.addItemDecoration(mItemDecoration);
        rlv.setAdapter(mListAdapter);
        mListAdapter.setOnItemClickListener(new HomepageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
    }

}
