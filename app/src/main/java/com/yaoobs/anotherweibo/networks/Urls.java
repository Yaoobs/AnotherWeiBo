package com.yaoobs.anotherweibo.networks;

/**
 * Created by yaoobs on 2016/11/15.
 */

public class Urls {
    private static final String PREFIX ="https://api.weibo.com/2/";
    public static final String HOME_TIME_LINE =PREFIX+"statuses/home_timeline.json";
    public static final String USER_TIME_LINE =PREFIX+"statuses/user_timeline.json";
    public static final String STATUS_REPOST =PREFIX+"statuses/repost.json";
    public static final String COMMENT_CREATE =PREFIX+"comments/create.json";
}
