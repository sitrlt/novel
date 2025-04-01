package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.BookInventoryMapper;
import com.example.novel.mapper.BorrowRecordMapper;
import com.example.novel.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class BorrowRecordController {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Autowired
    private BookInventoryMapper bookInventoryMapper;

    @GetMapping("/borrowRecord/findById/{readerId}")
    public List<BorrowRecord> findByReaderId(@PathVariable("readerId") Integer readerId) {
        List<BorrowRecord> records = borrowRecordMapper.selectByReaderId(readerId);
        if (records == null || records.isEmpty()) {
            return Collections.emptyList(); // 或者返回自定义的错误信息
        }
        return records;
    }

    @PostMapping("/borrowRecord/create")
    public String createBorrowRecord(@RequestBody BorrowRecord borrowRecord) {
        Integer readerId = borrowRecord.getReaderId();
        String bookIsbn = borrowRecord.getBookIsbn();

        QueryWrapper<BorrowRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("reader_id", readerId)
                .eq("book_isbn", bookIsbn);

        long count = borrowRecordMapper.selectCount(wrapper);
        if (count > 0) {
            return "该读者与书籍的组合已存在，无需重复插入";
        } else {
            boolean result = borrowRecordMapper.insertBorrowRecord(borrowRecord) > 0;
            if (result) {
                return "插入成功";
            } else {
                return "插入失败";
            }
        }
    }
    @GetMapping("/physicalBorrowRecord/findAll")
    public List<BorrowRecord> find(){
        return borrowRecordMapper.selectAllPhysicalBorrowRecords();
    }

    @GetMapping("/physicalBorrowRecord/findByPage")
    public IPage getBorrowRecordList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<BorrowRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BorrowRecord> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return borrowRecordMapper.selectPageWithPhysicalBorrowRecord(page,null);
    }

    @GetMapping("/borrowRecord/findByPage")
    public IPage<BorrowRecord> getBorrowRecordList2(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam int readerId) {//分别表示当前页码和每页数据条数
        // 创建分页对象
        Page<BorrowRecord> page = new Page<>(pageNum, pageSize);
        // 调用 Mapper 方法进行分页查询
        return borrowRecordMapper.selectPage1(page, readerId);
    }

    @GetMapping("/EbookBorrowRecord/findAll")
    public List<BorrowRecord> find1(){
        return borrowRecordMapper.selectAllEbookBorrowRecords();
    }

    @GetMapping("/EbookBorrowRecord/findByPage")
    public IPage getBorrowRecordList1(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                     @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<BorrowRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BorrowRecord> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return borrowRecordMapper.selectPageWithEbookBorrowRecord(page,null);
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
        int result = borrowRecordMapper.updateById(borrowRecord);
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

    @PutMapping("/borrowRecord/addCount/{uid}")
    public BorrowRecord addBorrowRecord(@PathVariable("uid") int id, @RequestBody BorrowRecord borrowRecord) {
        BorrowRecord exist = borrowRecordMapper.selectById(id);
        if(exist != null){
            borrowRecord.setId(id);
            borrowRecordMapper.updateById(borrowRecord);
            bookInventoryMapper.creaseAvailableCount(borrowRecord.getBookIsbn());
            return borrowRecord;
        }else{
            return null;
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

    // 获取不同书籍的借阅次数统计
    @GetMapping("/borrow/statistics/book")
    public Map<String, Integer> getBorrowsByBookTitle() {
        List<Map<String, Object>> resultList = borrowRecordMapper.countBorrowsByBookTitle();
        Map<String, Integer> borrowsByBookTitle = new HashMap<>();
        for (Map<String, Object> result : resultList) {
            String title = (String) result.get("title");
            Integer count = ((Long) result.get("borrowCount")).intValue();
            borrowsByBookTitle.put(title, count);
        }
        return borrowsByBookTitle;
    }

    @GetMapping("/borrow/statistics/reader")
    public Map<String, Integer> getBorrowsByReaderName() {
        List<Map<String, Object>> resultList = borrowRecordMapper.countBorrowsByReaderName();
        Map<String, Integer> borrowsByReaderName = new HashMap<>();
        for (Map<String, Object> result : resultList) {
            String username = (String) result.get("username");
            if (username != null) {
                Integer count = ((Long) result.get("borrowCount")).intValue();
                borrowsByReaderName.put(username, count);
            }
        }
        return borrowsByReaderName;
    }

    // 获取每日的借阅数量统计
    @GetMapping("/borrow/statistics/day")
    public Map<String, Integer> getBorrowsByDay() {
        List<Map<String, Object>> resultList = borrowRecordMapper.countBorrowsByDay();
        Map<String, Integer> borrowsByDay = new LinkedHashMap<>(); // 使用 LinkedHashMap 以保持插入顺序
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map<String, Object> result : resultList) {
            String borrowDateStr = result.get("borrowDate").toString();
            Integer count = ((Number) result.get("borrowCount")).intValue();
            borrowsByDay.put(borrowDateStr, count);
        }
        return borrowsByDay;
    }

    @GetMapping("/borrow/publisher-label-counts/{id}")
    public List<Map<String, Object>> countBorrowsByPublisherId(@PathVariable int id) {
        return borrowRecordMapper.countBorrowsByPublisherId(id);
    }
    @GetMapping("/borrow/statisticsByDay/{id}")
    public Map<String, Integer> getDailyBorrowSales(@PathVariable int id) {
        List<Map<String, Object>> resultList = borrowRecordMapper.getDailySales(id);
        Map<String, Integer> dailySales = new LinkedHashMap<>(); // 使用 LinkedHashMap 以保持插入顺序
        for (Map<String, Object> result : resultList) {
            String borrowDateStr = result.get("borrow_date").toString();
            int sales = ((Number) result.get("daily_sales")).intValue();
            dailySales.put(borrowDateStr, sales);
        }
        return dailySales;
    }
}

