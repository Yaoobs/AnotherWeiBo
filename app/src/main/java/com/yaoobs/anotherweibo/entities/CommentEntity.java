package com.yaoobs.anotherweibo.entities;

/**
 * Created by yaoobs on 2016/11/19.
 */

public class CommentEntity {
    public String created_at;
    public long id;
    public String text;
    public String source;
    public UserEntity user;
    public String mid;
    public String idStr;
    public CommentEntity reply_comment;
}
