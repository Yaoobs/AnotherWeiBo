package com.yaoobs.anotherweibo.presenter;

import android.widget.ListAdapter;

import com.yaoobs.anotherweibo.adapters.HomepageListAdapter;

/**
 * Created by yaoobs on 2016/11/18.
 */

public interface HomePresenter extends BasePresenter{
    void requestHomeTimeLine();
    void requestUserTimeLine();
}
