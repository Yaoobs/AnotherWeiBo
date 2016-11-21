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
    public static final String COMMENT_SHOW =PREFIX+"comments/show.json";
    public static final String MENTIONS =PREFIX+"statuses/mentions.json";
    public static final String COMMENTS_BY_ME =PREFIX+"comments/by_me.json";
    public static final String FAVOURITES =PREFIX+"favorites.json";
    public static final String USER_INFO =PREFIX+"users/show.json";
    public static final String FRIENDS =PREFIX+"friendships/friends.json";
    public static final String FOLLOWERS =PREFIX+"friendships/followers.json";
}
