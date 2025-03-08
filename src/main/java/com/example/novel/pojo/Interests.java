package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Interests {
    @TableField("reader_id")
    private int readerId;
    @TableField("label_id")
    private int labelId;
}
