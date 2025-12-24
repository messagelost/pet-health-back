package com.jacob.common.model.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
public class Comment {
    private String comment_id;
    private Long create_time; // 时间戳（毫秒）
    private String ip_location;
    private String note_id;
    private String content;   // 评论内容
    private String user_id;
    private String nickname;
    private String avatar;
    private Integer sub_comment_count;
    private String pictures;
    private String parent_comment_id;
    private Long last_modify_ts;
    private String like_count;
}