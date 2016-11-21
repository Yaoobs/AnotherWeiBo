package com.yaoobs.anotherweibo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaoobs.anotherweibo.utils.CircleTransform;

/**
 * Created by yaoobs on 2016/11/21.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {
    /**
     * Views indexed with their IDs
     */
    private final SparseArray<View> views;
    private View convertView;
    private Context mContext;

    public CommonViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
        mContext = itemView.getContext();
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int resId, String text) {
        TextView view = getView(resId);
        view.setText(text);
    }

    public void setImageByUrl(int resId, String url) {
        ImageView imageView = getView(resId);
        Glide.with(mContext).load(url).asBitmap().into(imageView);
    }
    public void setImageByUrlToCircle(int resId, String url) {
        ImageView imageView = getView(resId);
        Glide.with(mContext).load(url).asBitmap().transform(new CircleTransform(mContext)).into(imageView);
    }
}
