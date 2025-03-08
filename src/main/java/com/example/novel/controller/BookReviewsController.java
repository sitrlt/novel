package com.example.novel.controller;

import com.example.novel.mapper.BookReviewsMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.BookReviews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookReviewsController {
    @Autowired
    private BookReviewsMapper bookReviewsMapper;

    @GetMapping("/bookReviews/findById/{bookId}")
    public List<BookReviews> findBookReviews(@PathVariable("bookId")int bookId){
        return bookReviewsMapper.selectByBookId(bookId);
    }

    @PostMapping("/bookReviews/create")
    public void createBook(@RequestBody BookReviews bookReviews) {
        bookReviewsMapper.insertBookReview(bookReviews);
    }

    @GetMapping("/bookReviews/countByBookId/{bookId}")
    public int count(@PathVariable("bookId")int bookId){
        return bookReviewsMapper.countByBookId(bookId);
    }
}
