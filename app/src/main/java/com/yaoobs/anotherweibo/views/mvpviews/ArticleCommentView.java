package com.yaoobs.anotherweibo.views.mvpviews;

import android.app.Activity;

import com.yaoobs.anotherweibo.entities.CommentEntity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/19.
 */

public interface ArticleCommentView extends BaseView{
    void onSuccess(List<CommentEntity> list);
}
