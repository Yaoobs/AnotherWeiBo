package com.yaoobs.anotherweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.adapters.CommonAdapter;
import com.yaoobs.anotherweibo.adapters.FansAdapter;
import com.yaoobs.anotherweibo.entities.UserEntity;
import com.yaoobs.anotherweibo.presenter.FansPresenter;
import com.yaoobs.anotherweibo.presenter.FansPresenterImp;
import com.yaoobs.anotherweibo.utils.DividerItemDecoration;
import com.yaoobs.anotherweibo.utils.LogUtils;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;
import com.yaoobs.anotherweibo.views.mvpviews.FansView;

import java.util.ArrayList;
import java.util.List;

public class FansActivity extends BaseActivity implements FansView {

    private PullToRefreshRecyclerView rlv;
    private RecyclerView.ItemDecoration mItemDecoration;
    private RecyclerView.LayoutManager mManager;
    private FansPresenter mFansPresenter;
    private FansAdapter mFansAdapter;
    private List<UserEntity> mDataSet;
    private String action;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_my_attention);
        action = getIntent().getAction();
        mFansPresenter = new FansPresenterImp(this);
        switch (action){
            case "tvAttentions":
                getToolbar().setTitle(R.string.lbl_my_attention);
                mFansPresenter.requestMyAttentions();
                break;
            case "tvFans":
                getToolbar().setTitle(R.string.lbl_my_fans);
                mFansPresenter.requestMyFans();
                break;
        }
        initialize();
    }

    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
    }

    private void initialize() {

        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rlv.addItemDecoration(mItemDecoration);
        mManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mManager);
        mDataSet = new ArrayList<>();
        mFansAdapter = new FansAdapter(mDataSet);
        mFansPresenter.loadData(true);
        rlv.setAdapter(mFansAdapter);
        mFansAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            public void onItemClick(View v, int position) {
                LogUtils.e(mDataSet.get(position).screen_name);
            }
        });
    }

    public void onSuccess(List<UserEntity> list) {
        rlv.onRefreshComplete();
        if(null!=list&&list.size()>0){
            mDataSet.clear();
            mDataSet.addAll(list);
            mFansAdapter.notifyDataSetChanged();
        }
    }

    public void onError(String error) {
        rlv.onRefreshComplete();
        super.onError(error);
    }
}
