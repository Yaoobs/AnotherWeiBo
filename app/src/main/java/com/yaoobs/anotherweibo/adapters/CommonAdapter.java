package com.yaoobs.anotherweibo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/21.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    private List<T> mDataSet;
    private final int layoutId;
    private Context mContext;
    private OnItemClickListener mListener;

    public CommonAdapter(List<T> dataSet, int layoutId) {
        mDataSet = dataSet;
        this.layoutId = layoutId;
    }

    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent,false);
        return new CommonViewHolder(view);
    }

    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        convert(holder, mDataSet.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public abstract void convert(CommonViewHolder holder, T t);

    public int getItemCount() {
        return mDataSet.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
