package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.BookInventoryMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.BookInventory;
import com.example.novel.pojo.BorrowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookInventoryController {
    @Autowired
    private BookInventoryMapper bookInventoryMapper;

    @GetMapping("/bookInventory/findAll/{publisherId}")
    public List<BookInventory> find(@PathVariable("publisherId") int publisherId){
        return bookInventoryMapper.selectAllBookInventory(publisherId);
    }

    @GetMapping("/bookInventory/findByPage")
    public IPage<BookInventory> getBookInventoryPage(
            @RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam int publisherId) {
        // 创建分页对象
        Page<BookInventory> page = new Page<>(pageNum, pageSize);
        // 调用 Mapper 方法进行分页查询
        return bookInventoryMapper.selectPageWithPublisher(page, publisherId);
    }
    //通过id修改信息
    @PutMapping("/bookInventory/uid/{uid}")
    public BookInventory updateBookInventory(@PathVariable("uid") int id,@RequestBody BookInventory bookInventory){
        BookInventory exist = bookInventoryMapper.selectById(id);
        if(exist != null){
            bookInventory.setId(id);
            bookInventoryMapper.updateById(bookInventory);
            return bookInventory;
        }else{
            return null;
        }
    }
    //通过id删除信息
    @DeleteMapping("/bookInventory/id/{id}")
    public int delBookInventory(@PathVariable("id") int id){
        return bookInventoryMapper.deleteById(id);
    }

    @PostMapping("/bookInventory/create")
    public ResponseEntity<?> createBook(@RequestBody BookInventory book) {
            bookInventoryMapper.insert(book);
            return ResponseEntity.ok(book);
    }

    @GetMapping("/bookInventory/availableCount/{isbn}")
    public Integer getAvailableCount(@PathVariable String isbn) {
        return bookInventoryMapper.getAvailableCountByIsbn(isbn);
    }
    @GetMapping("/bookInventory/search")
    public List<BookInventory> searchBooks(@RequestParam("keyword") String keyword) {
        return bookInventoryMapper.searchBookInventory(keyword);
    }

}
