package com.example.novel.controller;


import com.example.novel.mapper.BookChapterMapper;
import com.example.novel.pojo.BookChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class BookChapterController {
    @Autowired
    private BookChapterMapper bookChapterMapper;

    @GetMapping("/bookChapter/{bookId}")
    public List<BookChapter> getBookChapterByBookId(@PathVariable("bookId") int bookId){
        return bookChapterMapper.getBookChapterByBookId(bookId);
    }
}
