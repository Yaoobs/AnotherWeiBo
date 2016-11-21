package com.yaoobs.anotherweibo.views.mvpviews;

import com.yaoobs.anotherweibo.entities.UserEntity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public interface FansView extends BaseView{
    void onSuccess(List<UserEntity> list);
}
