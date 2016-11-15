package com.yaoobs.anotherweibo.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yaoobs on 2016/11/15.
 */

public class LogUtils {
    private static final String TAG = "AnotherWeibo";
    public static void e(String message){
        if(!TextUtils.isEmpty(message)){
            Log.e(TAG,message);
        }
    }
    public static void d(String message){
        if(!TextUtils.isEmpty(message)){
            Log.d(TAG, message);
        }
    }
    public static void o(String tag,String message){
        if(!TextUtils.isEmpty(message)){
            Log.d(tag,message);
        }
    }
}
