package com.example.novel.controller;


import com.example.novel.mapper.LibrarianMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.Librarian;
import com.example.novel.pojo.Reader;
import com.example.novel.pojo.local;
import com.example.novel.service.clientServer;
import com.example.novel.service.mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class LibrarianController {
    @Autowired
    private LibrarianMapper librarianMapper;

    @PostMapping("/Librarian/login")
    public ResponseEntity<Librarian> findLibrarian(@RequestBody Map<String, String> requestBody) {
        String employeeId = requestBody.get("employeeId");
        String password = requestBody.get("password");

        if (employeeId == null || password == null) {
            return ResponseEntity.badRequest().body(null); // 返回400错误
        }

        Librarian user = librarianMapper.selectByEmployeeId(employeeId);
        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok(user); // 返回用户信息
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 返回401错误
        }
    }

    @GetMapping("/Librarian/findById/{id}")
    public Librarian findBook(@PathVariable("id") int id){
        return librarianMapper.selectById(id);
    }

    @PostMapping("/librarian/forget")
    public boolean findEmail(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        System.out.println("email"+email);
        //查找stu
        List<Librarian> list = librarianMapper.selectAlllibrarians();
        System.out.println(list.get(0));
        Librarian librarian = new Librarian();
        for(Librarian r:list)
        {
            System.out.println(r.getEmail());
            if(r.getEmail().equals(email)&&r.getEmail()!=null)
            {
                librarian=r;
            }
        }
//
        if (librarian != null) {
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

    @PostMapping("/librarian/update")
    public boolean changePassword(@RequestBody Map<String, String> loginData) throws Exception {
        boolean b = false;
        String code = loginData.get("code");
        String email = loginData.get("email");
        String newPassword = loginData.get("password");
        System.out.println(local.MESSAGE.get(email));
        System.out.println(email);
        if(code.equals(local.MESSAGE.get(email)))//验证码相同
        {
            Librarian r=null;
            List<Librarian> list = librarianMapper.selectAlllibrarians();
            for(Librarian su:list)
            {
                if(su.getEmail().equals(email))
                {
                    System.out.println(su.getEmail()+email);
                    r=su;
                    b=true;
                    local.MESSAGE.remove(email);
                }
            }
            if(r!=null)
            {
                System.out.println("r"+r);
                r.setPassword(newPassword);
                librarianMapper.updateById(r);//更新
            }
        }
        return b;
    }

}
