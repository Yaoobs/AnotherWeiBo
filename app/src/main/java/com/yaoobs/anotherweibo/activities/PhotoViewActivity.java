package com.yaoobs.anotherweibo.activities;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.entities.PicUrlsEntity;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends BaseActivity {

    private PhotoView photoview;
    private PicUrlsEntity mPicUrlsEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        mPicUrlsEntity = (PicUrlsEntity) getIntent().getSerializableExtra(PicUrlsEntity.class.getSimpleName());
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    private void initialize() {
        photoview = (PhotoView) findViewById(R.id.photoview);
        Glide.with(this).load(mPicUrlsEntity.original_pic).asBitmap().fitCenter().into(photoview);
    }
}
