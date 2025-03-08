package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BookLabels {
    @TableField("book_id")
    private int bookId;
    @TableField("label_id")
    private int labelId;
}
