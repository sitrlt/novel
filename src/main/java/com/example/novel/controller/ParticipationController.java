package com.example.novel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.novel.mapper.ParticipationMapper;
import com.example.novel.pojo.Participation;
import com.example.novel.pojo.Participation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ParticipationController {
    
    @Autowired
    private ParticipationMapper participationMapper;

    @PostMapping("/participation/create")
    public String createParticipation(@RequestBody Participation participation) {
        Integer readerId = participation.getReaderId();
        Integer activityId = participation.getActivityId();

        QueryWrapper<Participation> wrapper = new QueryWrapper<>();
        wrapper.eq("reader_id", readerId)
                .eq("activity_id", activityId);

        Participation existing = participationMapper.selectOne(wrapper);
        if (existing!= null) {
            // 数据已存在，这里可以执行其他逻辑，比如返回提示信息等
            return "该读者与活动的组合已存在，无需重复插入";
        } else {
            // 数据不存在，执行插入操作
            boolean result = participationMapper.insertParticipation(participation) > 0;
            if (result) {
                return "插入成功";
            } else {
                return "插入失败";
            }
        }
    }

    @GetMapping("/participation/findById/{id}")
    public List<Participation> findBook(@PathVariable("id") int id){
        return participationMapper.selectParticipationByReaderId(id);
    }

}
