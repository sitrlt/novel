package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel.mapper.PublisherMapper;
import com.example.novel.pojo.Librarian;
import com.example.novel.pojo.Publisher;
import com.example.novel.pojo.Publisher;
import com.example.novel.service.MD5Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherMapper publisherMapper;

    @GetMapping("/{id}")
    public Map<String, Object> getPublisherName(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Publisher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Publisher publisher = publisherMapper.selectOne(queryWrapper);
        if (publisher != null) {
            result.put("publisherName", publisher.getName());
            result.put("status", 200);
        } else {
            result.put("message", "未找到该出版社");
            result.put("status", 404);
        }
        return result;
    }

    //登录
    MD5Get md5Get = new MD5Get();
    @PostMapping("/login")
    public ResponseEntity<Publisher> findStu(@RequestBody Publisher publisher) throws Exception {
        // 获取登录表单数据
        String id = publisher.getPhone();
        String password = publisher.getPassword();
        // 查询数据库获取用户信息
        Publisher r = publisherMapper.selectByPhone(id);
        // 验证用户名和密码
        System.out.println(r);
        if (r != null && r.getPassword().equals(password)) {
            return ResponseEntity.ok(r); // 返回用户信息
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 返回401错误
        }
    }

    @GetMapping("/findById/{id}")
    public Publisher findBook(@PathVariable("id") int id){
        return publisherMapper.selectById(id);
    }


    @GetMapping("/findAll")
    public List<Publisher> findAll(){
        return publisherMapper.selectAllPublisher();
    }
}