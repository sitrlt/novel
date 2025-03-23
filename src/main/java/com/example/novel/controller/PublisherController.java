package com.example.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel.mapper.PublisherMapper;
import com.example.novel.pojo.*;
import com.example.novel.pojo.Publisher;
import com.example.novel.service.MD5Get;
import com.example.novel.service.clientServer;
import com.example.novel.service.mail;
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


    @PostMapping("/forget")
    public boolean findEmail(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        System.out.println("email"+email);
        //查找stu
        List<Publisher> list = publisherMapper.selectAllPublisher();
        System.out.println(list.get(1));
        Publisher publishers = new Publisher();
        for(Publisher r:list)
        {
            System.out.println(r.getEmail());
            if(r.getEmail().equals(email)&&r.getEmail()!=null)
            {
                publishers=r;
            }
        }
        if (publishers != null) {
            mail ma = new mail();
            clientServer s = new clientServer();
            String message = s.creatID();
            local.MESSAGE.put(email,message);//存在本地类
            ma.sendMail(email, "欢迎使用课程管理系统，这是系统为你生成的验证码：" + message + "。用于修改密码，请勿泄露！", "这是一封来自课程管理系统的验证邮件");
            return true;
        } else {
            return false;
        }
    }
    @PostMapping("/update")
    public boolean changePassword(@RequestBody Map<String, String> loginData) throws Exception {
        boolean a = false;
        String code = loginData.get("code");
        String email = loginData.get("email");
        String newPassword = loginData.get("password");
        System.out.println(local.MESSAGE.get(email));
        System.out.println(email);
        if(code.equals(local.MESSAGE.get(email)))//验证码相同
        {
            Publisher r=null;
            List<Publisher> list = publisherMapper.selectAllPublisher();
            for(Publisher su:list)
            {
                if(su.getEmail().equals(email))
                {
                    System.out.println(su.getEmail()+email);
                    r=su;
                    a=true;
                    local.MESSAGE.remove(email);
                }
            }
            if(r!=null)
            {
                System.out.println("r"+r);
                r.setPassword(newPassword);
                publisherMapper.updateById(r);//更新
            }
        }
        return a;
    }

}