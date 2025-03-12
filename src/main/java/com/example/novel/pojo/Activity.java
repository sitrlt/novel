package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class Activity {
    private int id;
    @TableField("activity_name")
    private String activityName;
    @TableField("activity_description")
    private String activityDescription;
    @TableField("start_time")
    private String startTime;
    @TableField("end_time")
    private String endTime;
    @TableField("activity_status")
    private String activityStatus;
    @TableField("create_time")
    private java.sql.Timestamp createTime;

    // 自定义 getter 方法，将 Timestamp 转换为日期字符串
    public String getCreateTimeStr() {
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createTime);
        }
        return null;
    }
}
