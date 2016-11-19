package com.yaoobs.anotherweibo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.adapters.ArticleCommentAdapter;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.CommentEntity;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.presenter.ArticleCommentPresenter;
import com.yaoobs.anotherweibo.presenter.ArticleCommentPresenterImp;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;
import com.yaoobs.anotherweibo.views.mvpviews.ArticleCommentView;
import com.yaoobs.anotherweibo.views.mvpviews.BaseView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ArticleCommentActivity extends BaseActivity implements ArticleCommentView{
    private StatusEntity mStatusEntity;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticleCommentAdapter mAdapter;
    private SPUtils mSPUtils;
    private List<CommentEntity> mDataSet;
    private ArticleCommentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.title_weibo_detail);
//        mStatusEntity = (StatusEntity) getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
//        mSPUtils = SPUtils.getInstance(getApplicationContext());
        mPresenter = new ArticleCommentPresenterImp(this);
        mStatusEntity = mPresenter.getEntity();
        mDataSet = new ArrayList<>();
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
    }

    private void initialize() {
        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        mAdapter = new ArticleCommentAdapter(this, mStatusEntity,mDataSet);
        rlv.setAdapter(mAdapter);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        mPresenter.loadData();
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadData();
            }

            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadMore();
            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onSuccess(List<CommentEntity> list) {
        rlv.onRefreshComplete();
        if(null!=list&&list.size()>0){
            mDataSet.clear();
            mDataSet.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        rlv.onRefreshComplete();
        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
    }
}
