package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.BookInventoryMapper;
import com.example.novel.mapper.BorrowRecordMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.BorrowRecord;
import com.example.novel.pojo.BorrowRecordDetail;
import com.example.novel.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BorrowRecordController {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Autowired
    private BookInventoryMapper bookInventoryMapper;

    @GetMapping("/borrowRecord/findById/{readerId}")
    public List<BorrowRecordDetail> findByReaderId(@PathVariable("readerId") Integer readerId) {
        List<BorrowRecordDetail> records = borrowRecordMapper.selectByReaderId(readerId);
        if (records == null || records.isEmpty()) {
            return Collections.emptyList(); // 或者返回自定义的错误信息
        }
        return records;
    }

    @PostMapping("/borrowRecord/create")
    public void createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
            borrowRecordMapper.insertBorrowRecord(borrowRecord);
    }

    @GetMapping("/borrowRecord/findAll")
    public List<BorrowRecord> find(){
        return borrowRecordMapper.selectAllBorrowRecords();
    }

    @GetMapping("/borrowRecord/findByPage")
    public IPage getBorrowRecordList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<BorrowRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BorrowRecord> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return borrowRecordMapper.selectPageWithBorrowRecord(page,null);
    }

    //通过id修改信息
    @PutMapping("/borrowRecord/uid/{uid}")
    public BorrowRecord updateBorrowRecord(@PathVariable("uid") int id,@RequestBody BorrowRecord borrowRecord){
        BorrowRecord exist = borrowRecordMapper.selectById(id);
        if(exist != null){
            borrowRecord.setId(id);
            borrowRecordMapper.updateById(borrowRecord);
            return borrowRecord;
        }else{
            return null;
        }
    }

    //两个结合
    @PutMapping("/borrowRecord/count/{id}")
    public String updateBorrowRecord(@PathVariable Integer id, @RequestBody BorrowRecord borrowRecord) {
        borrowRecord.setId(id);
        int result = borrowRecordMapper.updateBorrowRecordStatus(borrowRecord);
        if (result > 0) {
            // 查询当前书籍的可借数量
            Integer availableCount = bookInventoryMapper.getAvailableCountByIsbn(borrowRecord.getBookIsbn());
            if (availableCount != null && availableCount > 0) {
                // 更新库存
                int inventoryResult = bookInventoryMapper.decreaseAvailableCount(borrowRecord.getBookIsbn());
                if (inventoryResult > 0) {
                    return "借阅成功";
                } else {
                    return "库存更新失败";
                }
            } else {
                return "库存不足，无法借阅";
            }
        } else {
            return "借阅失败";
        }
    }

    //通过id删除信息
    @DeleteMapping("/borrowRecord/id/{id}")
    public int delBorrowRecord(@PathVariable("id") int id){
        return borrowRecordMapper.deleteById(id);
    }
    @GetMapping("/borrowRecord/search")
    public List<BorrowRecord> searchBorrowRecords(@RequestParam("keyword") String keyword) {
        return borrowRecordMapper.searchBorrowRecordsByKeyword(keyword);
    }

    /**
     * 获取按借阅数量排序的书籍信息
     * @return 包含书籍 ISBN 和借阅数量的 Map 列表
     */
    @GetMapping("/books/ranking")
    public List<Map<String, Object>> getBooksRanking() {
        return borrowRecordMapper.getBooksBorrowCount();
    }
}
