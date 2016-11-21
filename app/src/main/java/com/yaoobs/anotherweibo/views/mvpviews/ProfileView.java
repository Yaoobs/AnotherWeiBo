package com.yaoobs.anotherweibo.views.mvpviews;

import com.yaoobs.anotherweibo.entities.UserEntity;

/**
 * Created by yaoobs on 2016/11/21.
 */

public interface ProfileView extends BaseView{
    void onLoadUserInfo(UserEntity userEntity);
}
