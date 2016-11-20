package com.yaoobs.anotherweibo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.activities.CommentActivity;
import com.yaoobs.anotherweibo.activities.LikedActivity;
import com.yaoobs.anotherweibo.activities.MentionActivity;

/**
 * Created by yaoobs on 2016/11/20.
 */

public class DiscoverFragment extends BaseFragment  implements View.OnClickListener{
    private LinearLayout llAt;
    private LinearLayout llComments;
    private LinearLayout llLike;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover,container,false);
        initialize(view);

        return view;
    }

    private void initialize(View view) {

        llAt = (LinearLayout) view.findViewById(R.id.llAt);
        llComments = (LinearLayout) view.findViewById(R.id.llComments);
        llLike = (LinearLayout) view.findViewById(R.id.llLike);
        llAt.setOnClickListener(this);
        llComments.setOnClickListener(this);
        llLike.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent ;
        Class aClass = null;
        switch (v.getId()){
            case R.id.llAt:
                aClass= MentionActivity.class;
                break;
            case R.id.llComments:
                aClass = CommentActivity.class;
                break;
            case R.id.llLike:
                aClass = LikedActivity.class;
                break;
        }
        intent = new Intent(getActivity(),aClass);
        startActivity(intent);
    }
}
