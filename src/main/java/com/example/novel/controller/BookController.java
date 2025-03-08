package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.*;
import com.example.novel.pojo.*;
import com.example.novel.service.ContentBasedRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BookController {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ContentBasedRecommender recommender;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private BookLabelsMapper bookLabelsMapper;

    @Autowired
    private InterestsMapper interestsMapper;

    private static final String UPLOAD_DIR = "D:/web代码/novel/uploads/"; // 确保路径与静态资源目录一致


    @PostMapping("/book/upload-cover")
    public String uploadCoverImage(@RequestParam("file") MultipartFile file) {
        try {
            // 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // 构造文件保存路径
            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            // 保存文件到服务器
            file.transferTo(new File(filePath));
            // 返回可访问的图片路径
            return "http://localhost:8080/uploads/" + fileName; // 返回相对路径
        } catch (IOException e) {
            e.printStackTrace();
            return "文件保存失败：" + e.getMessage();
        }
    }


    @GetMapping("/book/findAll")
    public List<Book> find(){
        return bookMapper.selectAllBookAndPublisher();
    }

    @GetMapping("/book/findByPage")
    public IPage getBookList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Book> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return bookMapper.selectPageWithPublisher(page,null);
    }

    //出版社相关查询操作
    @GetMapping("/bookToPublisher/findAll/{publisherId}")
    public List<Book> find(@PathVariable("publisherId") int publisherId){
        return bookMapper.selectAllBookToPublisher(publisherId);
    }

    @GetMapping("/bookToPublisher/findByPage")
    public IPage<Book> getBookList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam int publisherId) {//分别表示当前页码和每页数据条数
        // 创建分页对象
        Page<Book> page = new Page<>(pageNum, pageSize);
        // 调用 Mapper 方法进行分页查询
        return bookMapper.selectPageToPublisher(page, publisherId);
    }



    @PostMapping("/book/create")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        // 在此处进行ID的重复性验证
        int count = bookMapper.existsById(book.getId());
        if (count >0) {
            // 如果存在重复ID，则返回响应
            String errorMessage = "ID already exists";
            return ResponseEntity.badRequest().body(errorMessage);
        } else {
            // 如果ID不重复，则执行插入操作
            bookMapper.insert(book);
            return ResponseEntity.ok(book);
        }
    }
    @GetMapping("/book/findById/{id}")
    public Book findBook(@PathVariable("id") int id){
        return bookMapper.selectBookById(id);
    }
    //通过id修改信息
    @PutMapping("/book/uid/{uid}")
    public Book updateBook(@PathVariable("uid") int id,@RequestBody Book book){
        Book exist = bookMapper.selectById(id);
        if(exist != null){
            book.setId(id);
            bookMapper.updateById(book);
            return book;
        }else{
            return null;
        }
    }

    //通过id删除信息
    @DeleteMapping("/book/id/{id}")
    public int delBook(@PathVariable("id") int id){
        return bookMapper.deleteById(id);
    }

    @GetMapping("/book/search")
    public List<Book> searchBooks(@RequestParam("keyword") String keyword) {
        return bookMapper.searchBooks(keyword);
    }

    @GetMapping("/book/searchNovel")
    public Map<String, Object> searchBooks(
            @RequestParam String keyword,
            @RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
            @RequestParam("pageSize") Integer pageSize) {
        // 创建分页对象
        Page<Book> pageParam = new Page<>(pageNum, pageSize);
        // 调用 Mapper 方法进行分页查询
        IPage<Book> bookPage = bookMapper.searchBooks(pageParam, keyword);
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("records", bookPage.getRecords());
        result.put("total", bookPage.getTotal());
        return result;
    }



// 根据读者 ID 获取读者信息并设置兴趣标签
   private Reader getReaderById(int readerId) {
       Reader reader = readerMapper.selectById(readerId);
       if (reader != null) {
           // 获取读者的兴趣标签
           List<Interests> readerInterests = interestsMapper.findByReaderId(readerId);
           List<Label> interestLabels = new ArrayList<>();
           for (Interests interest : readerInterests) {
               Label label = labelMapper.selectById(interest.getLabelId());
               if (label != null) {
                   interestLabels.add(label);
               }
           }
           reader.setInterests(interestLabels);
           // 打印日志，检查读者的兴趣标签是否正确
           System.out.println("Reader ID: " + readerId + ", Interests: " + interestLabels.stream().map(Label::getLabel).toList());
       }
       return reader;
   }

    // 为书籍设置标签
    private void setBookLabels(List<Book> books) {
        for (Book book : books) {
            List<BookLabels> bookLabels = bookLabelsMapper.findByBookId(book.getId());
            List<Label> bookLabelList = new ArrayList<>();
            for (BookLabels bookLabel : bookLabels) {
                Label label = labelMapper.selectById(bookLabel.getLabelId());
                if (label != null) {
                    bookLabelList.add(label);
                }
            }
            book.setLabels(bookLabelList);
            // 打印日志，检查每本书的标签是否正确
        }
    }

    @GetMapping("/book/interests/{readerId}")
    public List<Book> getInterests(@PathVariable int readerId) {
        Reader reader = getReaderById(readerId);
        if (reader != null) {
            List<Book> allBooks = bookMapper.selectAllBookAndPublisher();
            setBookLabels(allBooks);
            List<Book> recommendedBooks = recommender.recommendBooks(reader, allBooks);
            // 打印日志，检查推荐结果
            System.out.println("Recommended Books: " + recommendedBooks.stream().map(Book::getTitle).toList());
            return recommendedBooks;
        }
        return null;
    }

}
