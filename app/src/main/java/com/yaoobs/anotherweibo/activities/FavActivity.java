package com.yaoobs.anotherweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.adapters.FavListAdapter;
import com.yaoobs.anotherweibo.entities.FavEntity;
import com.yaoobs.anotherweibo.presenter.BasePresenter;
import com.yaoobs.anotherweibo.presenter.FavPresenterImp;
import com.yaoobs.anotherweibo.utils.DividerItemDecoration;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;
import com.yaoobs.anotherweibo.views.mvpviews.FavView;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends BaseActivity implements FavView {
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<FavEntity> mDataSet;
    private FavListAdapter mAdapter;
    private BasePresenter mBasePresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_fav);
        initialize();
    }

    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
    }

    public void onSuccess(List<FavEntity> list) {
        rlv.onRefreshComplete();
        if(null!=list&&list.size()>0){
            mDataSet.clear();
            mDataSet.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initialize() {

        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        mDataSet = new ArrayList<>();
        mItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        rlv.addItemDecoration(mItemDecoration);
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadData(false);

            }

            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadMore(false);
            }
        });
        mAdapter = new FavListAdapter(mDataSet,this);
        rlv.setAdapter(mAdapter);
        mBasePresenter = new FavPresenterImp(this);
        mBasePresenter.loadData(true);
    }
}
