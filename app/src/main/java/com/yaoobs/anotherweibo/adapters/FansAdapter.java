package com.yaoobs.anotherweibo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.entities.UserEntity;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public class FansAdapter extends CommonAdapter<UserEntity> {

    public FansAdapter(List<UserEntity> dataSet, int layoutId) {
        super(dataSet, R.layout.item_fans);
    }
    public FansAdapter(List<UserEntity> dataSet){
        super(dataSet,R.layout.item_fans);
    }

    public void convert(CommonViewHolder holder, UserEntity userEntity) {
        holder.setImageByUrlToCircle(R.id.ivHeader, userEntity.profile_image_url);
        holder.setText(R.id.tvUserName, userEntity.screen_name);
        holder.setText(R.id.tvDes, userEntity.description);
    }
}
