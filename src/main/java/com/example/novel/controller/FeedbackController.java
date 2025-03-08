package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.FeedbackMapper;
import com.example.novel.pojo.*;
import com.example.novel.pojo.Feedback;
import com.example.novel.pojo.Feedback;
import com.example.novel.pojo.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FeedbackController {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @PostMapping("/feedback/create")
    public void createFeedback(@RequestBody Feedback feedback) {
        feedbackMapper.insertFeedback(feedback);
    }

    @GetMapping("/feedback/findAll")
    public List<Feedback> find(){
        return feedbackMapper.selectAllFeedBack();
    }

    @GetMapping("/feedback/findByPage")
    public IPage getFeedbackList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return feedbackMapper.selectPageWithFeedback(page,null);
    }

    //通过id修改信息
    @PutMapping("/feedback/uid/{uid}")
    public Feedback updateFeedback(@PathVariable("uid") int id, @RequestBody Feedback feedback){
        Feedback exist = feedbackMapper.selectById(id);
        if(exist != null){
            feedback.setId(id);
            feedbackMapper.updateById(feedback);
            return feedback;
        }else{
            return null;
        }
    }

    //通过id删除信息
    @DeleteMapping("/feedback/id/{id}")
    public int delFeedback(@PathVariable("id") int id){
        return feedbackMapper.deleteById(id);
    }
    @GetMapping("/feedback/search")
    public List<Feedback> searchFeedbacks(@RequestParam("keyword") String keyword) {
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("content", keyword);
        return feedbackMapper.selectList(queryWrapper);
    }
    @GetMapping("/feedback/findById/{id}")
    public List<Feedback> findBook(@PathVariable("id") int id){
        return feedbackMapper.selectFeedbackByReaderId(id);
    }

    @DeleteMapping("/feedback/delete/{readerId}")
    public ResponseEntity<?> deleteFeedbacksByReaderId(@PathVariable("readerId") Long readerId) {
        feedbackMapper.deleteFeedbacksByReaderId(readerId);
        return ResponseEntity.ok().build();
    }

    //出版社相关查询操作
    @GetMapping("/feedbackToPublisher/findAll")
    public List<Feedback> findFeedbackToPublisher(){
        return feedbackMapper.selectAllFeedBackToPublisher();
    }

    @GetMapping("/feedbackToPublisher/findByPage")
    public IPage getFeedbackToPublisherList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                 @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Feedback> page1 = new Page<>(pageNum, pageSize);
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page1.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return feedbackMapper.selectPageToPublisher(page1,null);
    }

}
