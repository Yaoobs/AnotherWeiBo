package com.yaoobs.anotherweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.adapters.MentionListAdapter;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.presenter.BasePresenter;
import com.yaoobs.anotherweibo.presenter.MentionPresenterImp;
import com.yaoobs.anotherweibo.utils.DividerItemDecoration;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;
import com.yaoobs.anotherweibo.views.mvpviews.HomeView;

import java.util.ArrayList;
import java.util.List;

public class MentionActivity extends BaseActivity implements HomeView {
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<StatusEntity> mDataSet;
    private MentionListAdapter mAdapter;
    private BasePresenter mBasePresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.at_me);
        initialize();
    }

    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
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
        mAdapter = new MentionListAdapter(mDataSet,this);
        rlv.setAdapter(mAdapter);
        mBasePresenter = new MentionPresenterImp(this);
        mBasePresenter.loadData(true);

      /*  new BaseNetWork(this, CWUrls.MENTIONS) {
            public WeiboParameters onPrepare() {
                WeiboParameters parameters = new WeiboParameters(CWConstant.APP_KEY);
                parameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, SPUtils.getInstance(getApplicationContext())
                        .getToken().getToken());
                parameters.put(ParameterKeySet.PAGE, 1);
                parameters.put(ParameterKeySet.COUNT, 10);
                return parameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if(success){
                    LogUtils.e(response.response);
                }
            }
        }.get();*/
    }

    public void onSuccess(List<StatusEntity> list) {
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
