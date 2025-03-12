package com.example.novel.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class Participation {
    private Integer id;
    @TableField("reader_id")
    private Integer readerId;
    @TableField("activity_id")
    private Integer activityId;
    @TableField("participation_time")
    private java.sql.Timestamp participationTime;
    @TableField("participation_status")
    private String participationStatus;
    @TableField(exist = false)
    private Activity activity;
    // 自定义 getter 方法，将 Timestamp 转换为日期字符串
    public String getParticipationTimeStr() {
        if (participationTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(participationTime);
        }
        return null;
    }
}