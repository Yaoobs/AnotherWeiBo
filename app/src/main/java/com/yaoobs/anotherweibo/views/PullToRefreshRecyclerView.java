package com.yaoobs.anotherweibo.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by yaoobs on 2016/11/18.
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context,attrs);
        recyclerView.setId(com.handmark.pulltorefresh.library.R.id.recyclerView);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        if (mRefreshableView.getChildCount()==0){
            return true;
        }
        int count = mRefreshableView.getChildCount();
        View view = mRefreshableView.getChildAt(count-1);
        int position = mRefreshableView.getChildLayoutPosition(view);
        if (position>=mRefreshableView.getAdapter().getItemCount()-1){
            return view.getBottom() <= mRefreshableView.getBottom();
        }
        return false;
    }

    @Override
    protected boolean isReadyForPullStart() {
        if (mRefreshableView.getChildCount()==0){
            return true;
        }
        View view = mRefreshableView.getChildAt(0);
        int position = mRefreshableView.getChildLayoutPosition(view);
        if (position==0){
            return view.getTop() == mRefreshableView.getTop();
        }
        return false;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager){
        mRefreshableView.setLayoutManager(manager);
    }
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        mRefreshableView.addItemDecoration(itemDecoration);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        mRefreshableView.setAdapter(adapter);
    }
}
