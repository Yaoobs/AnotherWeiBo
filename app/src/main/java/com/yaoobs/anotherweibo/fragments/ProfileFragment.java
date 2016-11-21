package com.yaoobs.anotherweibo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.activities.FansActivity;
import com.yaoobs.anotherweibo.entities.UserEntity;
import com.yaoobs.anotherweibo.presenter.ProfilePresenter;
import com.yaoobs.anotherweibo.presenter.ProfilePresenterImp;
import com.yaoobs.anotherweibo.views.mvpviews.ProfileView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener,ProfileView{
    private ImageView ivHeader;
    private TextView tvUserName;
    private TextView tvDes;
    private TextView tvAttention;
    private TextView tvFans;
    private TextView tvLoginOut;
    private ProfilePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePresenterImp(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initialize(view);
        mPresenter.loadUserInfo();
        return view;
    }

    private void initialize(View view) {

        ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvDes = (TextView) view.findViewById(R.id.tvDes);
        tvAttention = (TextView) view.findViewById(R.id.tvAttentions);
        tvFans = (TextView) view.findViewById(R.id.tvFans);
        tvLoginOut = (TextView) view.findViewById(R.id.tvLoginOut);
        tvAttention.setOnClickListener(this);
        tvFans.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tvAttentions:
                intent = new Intent(getActivity(),FansActivity.class);
                intent.setAction("tvAttentions");
                startActivity(intent);
                break;
            case R.id.tvFans:
                intent = new Intent(getActivity(),FansActivity.class);
                intent.setAction("tvFans");
                startActivity(intent);
                break;
        }
    }

    public void onLoadUserInfo(UserEntity userEntity) {
        Glide.with(getActivity()).load(userEntity.profile_image_url).into(ivHeader);
        tvUserName.setText(userEntity.screen_name);
        tvDes.setText(userEntity.description);
    }
}
