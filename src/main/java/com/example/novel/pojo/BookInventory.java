package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BookInventory {
    private int id;
    @TableField("book_isbn")
    private String bookIsbn;  // 对应表中的 book_id，遵循 Java 命名规范，使用驼峰命名法
    @TableField("total_copies")
    private int totalCopies;
    @TableField("publisher_id")
    private int publisherId;
    @TableField("available_copies")
    private int availableCopies;
    @TableField(exist = false)
    private Book book;
}