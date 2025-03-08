package com.example.novel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.LabelMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelMapper labelMapper;

    @GetMapping("/label/findAll")
    public List<Label> find(){
        return labelMapper.selectAllLabel();
    }

    @GetMapping("/label/findByPage")
    public IPage getLabelList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                             @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Label> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        IPage ipage = labelMapper.selectPage(page,null);//执行分页查询操作，使用 page 对象和 queryWrapper 对象进行查询，并返回符合条件的用户分页结果。
        return ipage;
    }

    @PostMapping("/label/create")
    public ResponseEntity<?> createLabel(@RequestBody Label label) {
        // 在此处进行ID的重复性验证
        int count = labelMapper.existsById(label.getId());
        if (count >0) {
            // 如果存在重复ID，则返回响应
            String errorMessage = "ID already exists";
            return ResponseEntity.badRequest().body(errorMessage);
        } else {
            // 如果ID不重复，则执行插入操作
            labelMapper.insert(label);
            return ResponseEntity.ok(label);
        }
    }
    @GetMapping("/label/findById/{id}")
    public Label findLabel(@PathVariable("id") int id){
        return labelMapper.selectById(id);
    }

    //通过id修改信息
    @PutMapping("/label/uid/{uid}")
    public Label updateLabel(@PathVariable("uid") int id,@RequestBody Label label){
        Label exist = labelMapper.selectById(id);
        if(exist != null){
            label.setId(id);
            labelMapper.updateById(label);
            return label;
        }else{
            return null;
        }
    }

    //通过id删除信息
    @DeleteMapping("/label/id/{id}")
    public int delLabel(@PathVariable("id") int id){
        return labelMapper.deleteById(id);
    }
    @GetMapping("/label/search")
    public List<Label> searchLabels(@RequestParam("keyword") String keyword) {
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("label", keyword);
        return labelMapper.selectList(queryWrapper);
    }
}
