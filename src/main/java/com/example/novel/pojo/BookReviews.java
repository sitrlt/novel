package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BookReviews {
    private Integer id;
    @TableField("reader_id")
    private Integer readerId;
    @TableField("book_id")
    private Integer bookId;
    @TableField("review_text")
    private String reviewText;
    private Integer rating;
    @TableField("review_date")
    private String reviewDate;
    @TableField(exist = false)
    private Reader reader; // Publisher 对象
    @TableField(exist = false)
    private Book book;
}