package com.example.novel.pojo;

import lombok.Data;

@Data
public class BorrowRecordDetail {
    private BorrowRecord borrowRecord;
    private Book book;
}