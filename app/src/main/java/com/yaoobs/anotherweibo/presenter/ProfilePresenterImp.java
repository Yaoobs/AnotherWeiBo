package com.yaoobs.anotherweibo.presenter;

import android.content.Intent;

import com.google.gson.Gson;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yaoobs.anotherweibo.activities.LandingPageActivity;
import com.yaoobs.anotherweibo.core.Constant;
import com.yaoobs.anotherweibo.entities.HttpResponse;
import com.yaoobs.anotherweibo.entities.UserEntity;
import com.yaoobs.anotherweibo.networks.BaseNetWork;
import com.yaoobs.anotherweibo.networks.ParameterKeySet;
import com.yaoobs.anotherweibo.networks.Urls;
import com.yaoobs.anotherweibo.utils.SPUtils;
import com.yaoobs.anotherweibo.views.mvpviews.ProfileView;

/**
 * Created by yaoobs on 2016/11/21.
 */

public class ProfilePresenterImp implements ProfilePresenter {
    private ProfileView mProfileView;
    private SPUtils mSPUtils;
    private WeiboParameters mParameters;

    public ProfilePresenterImp(ProfileView profileView) {
        mProfileView = profileView;
        mSPUtils = SPUtils.getInstance(profileView.getActivity());
        mParameters = new WeiboParameters(Constant.APP_KEY);
    }

    public void loadUserInfo() {
        new BaseNetWork(mProfileView, Urls.USER_INFO) {
            public WeiboParameters onPrepare() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN,mSPUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.GAME_PARAMS_UID,mSPUtils.getToken().getUid());
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if(success){
                    UserEntity entity = new Gson().fromJson(response.response,UserEntity.class);
                    mProfileView.onLoadUserInfo(entity);
                }

            }
        }.get();
    }

    public void logOut() {
        mSPUtils.logOut();
        mProfileView.getActivity().startActivity(new Intent(mProfileView.getActivity(), LandingPageActivity.class));
        mProfileView.getActivity().finish();
    }
}
