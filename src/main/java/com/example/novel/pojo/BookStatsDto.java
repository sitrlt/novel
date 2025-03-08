package com.example.novel.pojo;

import lombok.Data;

@Data
public class BookStatsDto {
    private int total;
    private int borrowing;
    private int borrowingCount;
    private int returned;
    private int returnedCount;
    private int overdue;
    private int overdueCount;
}