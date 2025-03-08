package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class Reservation {
    private int id;
    @TableField("reader_id")
    private int readerId;
    @TableField("book_isbn")
    private String bookIsbn;
    @TableField("reserve_date")
    private java.sql.Timestamp reserveDate;
    private String status;
    @TableField("admin_response")
    private String adminResponse;
    @TableField(exist = false)
    private Reader reader; // reader 对象
    @TableField(exist = false)
    private Book book; // book 对象
    @TableField(exist = false)
    private BookInventory bookInventory;

    // 自定义 getter 方法，将 Timestamp 转换为日期字符串
    public String getReserveDateStr() {
        if (reserveDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(reserveDate);
        }
        return null;
    }
}
