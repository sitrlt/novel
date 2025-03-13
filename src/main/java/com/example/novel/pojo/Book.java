package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Book {
    private int id;
    private String isbn;
    private String title;
    private String author;
    private String description;
    @TableField("cover_image")
    private String coverImage;
    @TableField("publish_date")
    private String publishDate;
    @TableField("publisher_id")
    private int publisherId;
    @TableField("is_payable")
    private int isPayable;
    @TableField("ebook_path")
    private String ebookPath;
    @TableField("borrowing_fee")
    private String borrowingFee;
    @TableField("is_ebook")
    private boolean isEbook;
    @TableField(exist = false)
    private Publisher publisher; // Publisher 对象
    @TableField(exist = false)
    private List<Label> labels;
    @TableField(exist = false)
    private BookInventory bookInventory;
    @TableField(exist = false)
    private List<BookReviews> bookReviews;
}
