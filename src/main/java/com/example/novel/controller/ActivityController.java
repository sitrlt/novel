package com.example.novel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.ActivityMapper;
import com.example.novel.pojo.Activity;
import com.example.novel.pojo.Activity;
import com.example.novel.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ActivityController {

    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping("/activity/findAll")
    public List<Activity> find(){
        return activityMapper.selectActivity();
    }

    @GetMapping("/activity/findByPage")
    public IPage getActivityList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                 @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Activity> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return activityMapper.selectPage(page,null);
    }

    @PostMapping("/activity/create")
    public ResponseEntity<?> createActivity(@RequestBody Activity activity) {
            activityMapper.insert(activity);
            return ResponseEntity.ok(activity);
    }

    //通过id修改信息
    @PutMapping("/activity/uid/{uid}")
    public Activity updateActivity(@PathVariable("uid") int id,@RequestBody Activity activity){
        Activity exist = activityMapper.selectById(id);
        if(exist != null){
            activity.setId(id);
            activityMapper.updateById(activity);
            return activity;
        }else{
            return null;
        }
    }

    //通过id删除信息
    @DeleteMapping("/activity/id/{id}")
    public int delActivity(@PathVariable("id") int id){
        return activityMapper.deleteById(id);
    }
    @GetMapping("/activity/search")
    public List<Activity> searchActivity(@RequestParam("keyword") String keyword) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("activity_name", keyword)
                .or().like("activity_status", keyword)
                .or().like("start_time",keyword)
                .or().like("end_time",keyword)
                .or().like("activity_description",keyword);
        return activityMapper.selectList(queryWrapper);
    }

    @GetMapping("/activity/findById/{id}")
    public Activity findBook(@PathVariable("id") int id){
        return activityMapper.selectById(id);
    }


}
