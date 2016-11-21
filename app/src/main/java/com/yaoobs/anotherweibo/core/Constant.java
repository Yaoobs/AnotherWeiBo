package com.yaoobs.anotherweibo.core;

/**
 * Created by yaoobs on 2016/11/15.
 */

public class Constant {
    public static final String APP_KEY = "3893252431";
    public static final String SECRET_KEY = "abb1e36934327bd8cdb8383e7d620c92";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE =                               // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static final boolean DEBUG_ACTIVITIES = true;
}
