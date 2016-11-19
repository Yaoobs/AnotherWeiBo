package com.yaoobs.anotherweibo.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaoobs on 2016/11/15.
 */

public class StatusEntity implements Serializable{

    /**
     * created_at : Tue Nov 15 15:50:10 +0800 2016
     * id : 4042104235403342
     * mid : 4042104235403342
     * idstr : 4042104235403342
     * text : ✨我分享的购物清单✨点赞完，别忘了拆红包🎊 http://t.cn/Rf58INl
     * textLength : 64
     * source_allowclick : 0
     * source_type : 1
     * source : <a href="http://app.weibo.com/t/feed/6EusKj" rel="nofollow">手机淘宝</a>
     * favorited : false
     * truncated : false
     * in_reply_to_status_id : 
     * in_reply_to_user_id : 
     * in_reply_to_screen_name : 
     */

    public String created_at;
    public long id;
    public String mid;
    public String idstr;
    public String text;
    public int textLength;
    public int source_allowclick;
    public int source_type;
    public String source;
    public boolean favorited;
    public boolean truncated;
    public String in_reply_to_status_id;
    public String in_reply_to_user_id;
    public String in_reply_to_screen_name;

    public String thumbnail_pic;
    public String bmiddle_pic;
    public String original_pic;
    public Object geo;
    public List<PicUrlsEntity> pic_urls;
    public UserEntity user;
    public StatusEntity retweeted_status;
    public  int  reposts_count;
    public  int  comments_count;
    public  int  attitudes_count;

    public int deleted;
}
