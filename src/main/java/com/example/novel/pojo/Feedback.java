package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class Feedback {
    private Integer id;
    @TableField("reader_id")
    private Integer readerId;
    private String content;
    private String status;
    @TableField("admin_response")
    private String adminResponse;
    @TableField("response_date")
    private String responseDate;
    @TableField("feedback_type")
    private String feedbackType;
    @TableField("target_entity")
    private String targetEntity;
    @TableField("created_at")
    private java.sql.Timestamp createdAt;
    @TableField("updated_at")
    private java.sql.Timestamp updatedAt;
    @TableField(exist = false)
    private Reader reader; // Publisher 对象


    // 自定义 getter 方法，将 Timestamp 转换为日期字符串
    public String getCreatedAtStr() {
        if (createdAt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createdAt);
        }
        return null;
    }
}