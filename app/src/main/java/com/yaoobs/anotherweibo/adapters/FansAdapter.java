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

public class FansAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private List<UserEntity> mList;

    public FansAdapter(List<UserEntity> list) {
        mList = list;
    }

    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_fans,null);
        return new CommonViewHolder(view);
    }

    public void onBindViewHolder(CommonViewHolder holder, int position) {
        UserEntity entity = mList.get(position);
        holder.setText(R.id.tvUserName,entity.screen_name);
        holder.setText(R.id.tvDes,entity.description);
        holder.setImageByUrlToCircle(R.id.ivHeader,entity.profile_image_url);
    }

    public int getItemCount() {
        return mList.size();
    }
}
