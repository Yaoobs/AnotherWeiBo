package com.yaoobs.anotherweibo.views.mvpviews;

import android.app.Activity;

import com.yaoobs.anotherweibo.entities.StatusEntity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/19.
 */

public interface HomeView extends BaseView{
    void onSuccess(List<StatusEntity> list);
}
