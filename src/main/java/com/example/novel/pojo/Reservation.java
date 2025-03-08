package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Reservation {
    private int id;
    @TableField("reader_id")
    private int readeId;
    @TableField("book_id")
    private int bookId;
    @TableField("reserve_date")
    private java.sql.Timestamp reserveDate;
    private String status;
}
