package com.example.novel.controller;


import com.example.novel.mapper.InterestsMapper;
import com.example.novel.pojo.Interests;
import com.example.novel.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class InterestsController {
    @Autowired
    private InterestsMapper interestsMapper;

    //通过id删除信息
    @DeleteMapping("/interests/labelId/{labelId}")
    public int delInterests(@PathVariable("labelId") int labelId){
        return interestsMapper.deleteInterests(labelId);
    }

    @PostMapping("/interests/create")
    public ResponseEntity<?> createLabel(@RequestBody Interests interests) {
            // 如果ID不重复，则执行插入操作
            interestsMapper.insert(interests);
            return ResponseEntity.ok(interests);
        }
}
