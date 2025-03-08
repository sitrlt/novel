package com.example.novel.controller;


import com.example.novel.mapper.LibrarianMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.Librarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
