package com.example.novel.controller;


import com.example.novel.mapper.BookLabelsMapper;
import com.example.novel.pojo.BookLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin

public class BookLabelsController {
    @Autowired
    private BookLabelsMapper bookLabelsMapper;

    //通过id删除信息
    @DeleteMapping("/bookLabels/delete")
    public String deleteBookLabel(@RequestParam("labelId") int labelId, @RequestParam("bookId") int bookId) {
        // 根据 labelId 和 bookId 删除对应的标签记录
        bookLabelsMapper.deleteBookLabels(labelId, bookId);
        return "Label deleted successfully";
    }

    @PostMapping("/bookLabels/create")
    public ResponseEntity<?> createLabel(@RequestBody BookLabels bookLabels) {
        // 如果ID不重复，则执行插入操作
        bookLabelsMapper.insert(bookLabels);
        return ResponseEntity.ok(bookLabels);
    }
}
