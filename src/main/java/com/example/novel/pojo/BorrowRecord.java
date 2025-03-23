package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class BorrowRecord {
    private Integer id; // 主键
    @TableField("reader_id")
    private Integer readerId; // 读者ID
    @TableField("book_isbn")
    private String  bookIsbn; // 书籍ID
    @TableField("due_date")
    private String dueDate; // 应还日期
    @TableField("return_date")
    private String returnDate; // 实际还书日期
    @TableField("borrow_date")
    private String borrowDate; // 借书日期
    @TableField("borrowing_fee")
    private int borrowingFee;
    private String status; // 状态
    private Double fine; // 罚款
    @TableField(exist = false)
    private Reader reader; // reader 对象
    @TableField(exist = false)
    private Book book; // book 对象
    @TableField(exist = false)
    private BookInventory bookInventory;
    @TableField(exist = false)
    private List<Label> labels;
}
