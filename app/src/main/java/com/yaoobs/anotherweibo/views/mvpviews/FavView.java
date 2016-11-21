package com.yaoobs.anotherweibo.views.mvpviews;

import com.yaoobs.anotherweibo.entities.FavEntity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public interface FavView extends BaseView{
    void onSuccess(List<FavEntity> list);
}
