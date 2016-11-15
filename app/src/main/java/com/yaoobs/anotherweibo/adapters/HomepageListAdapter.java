package com.yaoobs.anotherweibo.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.entities.StatusEntity;
import com.yaoobs.anotherweibo.utils.TimeFormatUtils;

import java.util.List;

/**
 * Created by yaoobs on 2016/11/15.
 */

public class HomepageListAdapter extends RecyclerView.Adapter{

    private List<StatusEntity> mDataSet;
    private OnItemClickListener mOnItemClickListener;


    public HomepageListAdapter(List<StatusEntity> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo_content,parent,false);
        return new HomepageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     if (holder instanceof HomepageViewHolder){
         HomepageViewHolder homepageViewHolder = (HomepageViewHolder) holder;
        StatusEntity entity = mDataSet.get(position);
         homepageViewHolder.tvUserName.setText(entity.user.screen_name);
         homepageViewHolder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
         homepageViewHolder.tvContent.setText(entity.text);
         homepageViewHolder.tvSource.setText(Html.fromHtml(entity.source).toString());
         StatusEntity reStatus = entity.retweeted_status;
         if (null!=reStatus){
             homepageViewHolder.llRe.setVisibility(View.VISIBLE);
             homepageViewHolder.tvReContent.setText(reStatus.text);
         } else {
             homepageViewHolder.llRe.setVisibility(View.GONE);
         }

     }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }



    class HomepageViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivHeader;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvSource;
        private TextView tvContent;
        private TextView tvReContent;
        private LinearLayout llRe;

        public HomepageViewHolder(View itemView) {
            super(itemView);
            initialize(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
        private void initialize(View v) {
            ivHeader = (ImageView) v.findViewById(R.id.ivHeader);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            tvSource = (TextView) v.findViewById(R.id.tvSource);
            tvContent = (TextView) v.findViewById(R.id.tvContent);
            tvReContent = (TextView) v.findViewById(R.id.tvReContent);
            llRe = (LinearLayout) v.findViewById(R.id.llRe);

        }
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
