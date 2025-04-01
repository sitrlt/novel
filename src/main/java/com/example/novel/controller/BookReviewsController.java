package com.example.novel.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @GetMapping("/bookReviews/search/{readerId}")
    public List<BookReviews> search(@PathVariable("readerId") int readerId){
        return bookReviewsMapper.selectByReaderId(readerId);
    }

    //通过id删除信息
    @DeleteMapping("/bookReviews/id/{id}")
    public int delBookReviews(@PathVariable("id") int id){
        return bookReviewsMapper.deleteById(id);
    }

    @GetMapping("/bookReviews/findByPage")
    public IPage<BookReviews> getBookReviewsList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam int readerId) {//分别表示当前页码和每页数据条数
        // 创建分页对象
        Page<BookReviews> page = new Page<>(pageNum, pageSize);
        // 调用 Mapper 方法进行分页查询
        return bookReviewsMapper.selectPageWithBookReviews(page, readerId);
    }
    @GetMapping("/bookReviews/findByPageByBookId")
    public IPage<BookReviews> getBookReviewsListByBookId(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam int bookId) {//分别表示当前页码和每页数据条数
        // 创建分页对象
        Page<BookReviews> page = new Page<>(pageNum, pageSize);
        // 调用 Mapper 方法进行分页查询
        return bookReviewsMapper.selectPageWithBookReviewsByBookId(page, bookId);
    }

}
