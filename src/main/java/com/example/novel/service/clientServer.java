package com.example.novel.service;

import java.time.LocalDateTime;
import java.time.Month;

public class clientServer {
    public String creatID()
    {
        SnowFlake idWorker = new SnowFlake(0, 0);
        long id = idWorker.nextId();
        while(id>99999999)
        {
            id= id/10;
        }
        return String.valueOf(id);
    }

    public String getTime(int x)
    {
        int currentYear = LocalDateTime.now().getYear();

        // 获取上半年的起始时间（1月1日 00:00:00）
        LocalDateTime firstHalfYearStart = LocalDateTime.of(currentYear, Month.JANUARY, 1, 0, 0, 0);
        String firstHalfYearStartString = firstHalfYearStart.toString();

        // 获取上半年的结束时间（6月30日 23:59:59）
        LocalDateTime firstHalfYearEnd = LocalDateTime.of(currentYear, Month.JUNE, 30, 23, 59, 59);
        String firstHalfYearEndString = firstHalfYearEnd.toString();

        // 获取下半年的起始时间（7月1日 00:00:00）
        LocalDateTime secondHalfYearStart = LocalDateTime.of(currentYear, Month.JULY, 1, 0, 0, 0);
        String secondHalfYearStartString = secondHalfYearStart.toString();

        // 获取下半年的结束时间（12月31日 23:59:59）
        LocalDateTime secondHalfYearEnd = LocalDateTime.of(currentYear, Month.DECEMBER, 31, 23, 59, 59);
        String secondHalfYearEndString = secondHalfYearEnd.toString();
        if(x==1)
            return firstHalfYearStartString;
        else if(x==2)
            return firstHalfYearEndString;
        else if(x==3)
            return secondHalfYearStartString;
        else return secondHalfYearEndString;
    }

}
