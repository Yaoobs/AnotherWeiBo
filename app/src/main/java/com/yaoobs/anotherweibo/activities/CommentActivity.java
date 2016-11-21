package com.yaoobs.anotherweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.adapters.CommentListAdapter;
import com.yaoobs.anotherweibo.entities.CommentEntity;
import com.yaoobs.anotherweibo.presenter.BasePresenter;
import com.yaoobs.anotherweibo.presenter.CommentPresenterImp;
import com.yaoobs.anotherweibo.utils.DividerItemDecoration;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;
import com.yaoobs.anotherweibo.views.mvpviews.CommentView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity implements CommentView {
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<CommentEntity> mDataSet;
    private CommentListAdapter mAdapter;
    private BasePresenter mBasePresenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_comment);
        initialize();
    }
    private void initialize() {

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
        mAdapter = new CommentListAdapter(mDataSet,this);
        rlv.setAdapter(mAdapter);
        mBasePresenter = new CommentPresenterImp(this);
        mBasePresenter.loadData(true);

    }

    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
    }

    public void onSuccess(List<CommentEntity> list) {
        rlv.onRefreshComplete();
        if(null!=list&&list.size()>0){
            mDataSet.clear();
            mDataSet.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void onError(String error) {
        super.onError(error);
        rlv.onRefreshComplete();
    }
}
