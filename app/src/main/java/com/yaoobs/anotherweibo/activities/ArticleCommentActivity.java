package com.yaoobs.anotherweibo.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
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
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.PullToRefreshRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ArticleCommentActivity extends BaseActivity {
    private StatusEntity mStatusEntity;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticleCommentAdapter mAdapter;
    private SPUtils mSPUtils;
    private List<CommentEntity> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.title_weibo_detail);
        mStatusEntity = (StatusEntity) getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
        mSPUtils = SPUtils.getInstance(getApplicationContext());
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
        new BaseNetWork(this, Urls.COMMENT_SHOW) {
            public WeiboParameters onPrepare() {
                WeiboParameters parameters = new WeiboParameters(Constant.APP_KEY);
                parameters.put(ParameterKeySet.ID, mStatusEntity.id);
                parameters.put(ParameterKeySet.PAGE, 1);
                parameters.put(ParameterKeySet.COUNT, 10);
                parameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
                return parameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    Type type = new TypeToken<ArrayList<CommentEntity>>() {
                    }.getType();
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(response.response);
                    if (element.isJsonArray()) {
                        List<CommentEntity> temp = new Gson().fromJson(element, type);
                        mDataSet.clear();
                        mDataSet.addAll(temp);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }.get();
    }
}
