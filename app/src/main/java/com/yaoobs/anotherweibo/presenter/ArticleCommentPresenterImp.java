package com.yaoobs.anotherweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.CommentEntity;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.LogUtils;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.mvpviews.ArticleCommentView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoobs on 2016/11/19.
 */

public class ArticleCommentPresenterImp implements ArticleCommentPresenter {
    private ArticleCommentView mCommentView;
    private SPUtils mSPUtils;
    private List<CommentEntity> mDataSet;
    private int page =1;

    public ArticleCommentPresenterImp(ArticleCommentView commentView) {
        mCommentView = commentView;
        mSPUtils = SPUtils.getInstance(commentView.getActivity().getApplicationContext());
        mDataSet = new ArrayList<>();
    }

    public StatusEntity getEntity() {
        return (StatusEntity) mCommentView.getActivity().getIntent().getSerializableExtra(StatusEntity.class
                .getSimpleName());
    }

    public void loadData() {
        page=1;
        loadData(false);
    }

    public void loadMore() {
        page++;
        loadData(true);
    }
    private void loadData(final boolean loadMore){
        new BaseNetWork(mCommentView.getActivity(), Urls.COMMENT_SHOW) {
            public WeiboParameters onPrepare() {
                WeiboParameters parameters = new WeiboParameters(Constant.APP_KEY);
                parameters.put(ParameterKeySet.ID, getEntity().id);
                parameters.put(ParameterKeySet.PAGE, page);
                parameters.put(ParameterKeySet.COUNT, 1);
                parameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mSPUtils.getToken().getToken());
                return parameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                LogUtils.e(response.response);
                if (success) {
                    Type type = new TypeToken<ArrayList<CommentEntity>>() {
                    }.getType();
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(response.response);
                    if (element.isJsonArray()) {
                        List<CommentEntity> temp = new Gson().fromJson(element, type);
                        if(!loadMore){
                            mDataSet.clear();
                        }
                        mDataSet.addAll(temp);
                        mCommentView.onSuccess(mDataSet);
                    }
                }
            }
        }.get();
    }
}
