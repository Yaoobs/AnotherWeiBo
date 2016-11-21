package com.yaoobs.anotherweibo.views.mvpviews;

import com.yaoobs.anotherweibo.entities.CommentEntity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public interface CommentView extends BaseView {
    void onSuccess(List<CommentEntity> list);

}
