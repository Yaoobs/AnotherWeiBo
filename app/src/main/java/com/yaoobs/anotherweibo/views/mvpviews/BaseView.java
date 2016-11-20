package com.yaoobs.anotherweibo.views.mvpviews;

import android.app.Activity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/19.
 */

public interface BaseView {
    Activity getActivity();
    void onError(String error);
    void showLoading();
    void hideLoading();
}
