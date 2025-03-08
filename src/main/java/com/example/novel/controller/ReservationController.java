package com.example.novel.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.mapper.ReservationMapper;
import com.example.novel.pojo.BorrowRecord;
import com.example.novel.pojo.BorrowRecordDetail;
import com.example.novel.pojo.Feedback;
import com.example.novel.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationMapper reservationMapper;

    @PostMapping("/reservation/create")
    public void createReservation(@RequestBody Reservation reservation) {
        reservationMapper.insert(reservation);
    }

    @GetMapping("/reservation/findById/{readerId}")
    public List<Reservation> findByReaderId(@PathVariable("readerId") Integer readerId) {
        List<Reservation> records = reservationMapper.selectByReaderId(readerId);
        if (records == null || records.isEmpty()) {
            return Collections.emptyList(); // 或者返回自定义的错误信息
        }
        return records;
    }

    @DeleteMapping("/reservation/delete/{id}")
    public void deleteReservation(@PathVariable("id")int readerId){
        reservationMapper.deleteById(readerId);
    }


    @GetMapping("/reservation/findAll")
    public List<Reservation> find(){
        return reservationMapper.selectAllPhysicalReservations();
    }

    @GetMapping("/reservation/findByPage")
    public IPage getReservationList(@RequestParam("pageNum") Integer pageNum,//使用 @RequestParam 注解来获取请求参数 pageNum 和 pageSize 的值，
                                     @RequestParam("pageSize") Integer pageSize) {//分别表示当前页码和每页数据条数
        Page<Reservation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();//创建一个 Page 对象，用于分页查询。传入 pageNum 和 pageSize 参数来指定当前页码和每页数据条数
        queryWrapper.orderByDesc("id"); // 根据id字段降序排序
        page.addOrder(OrderItem.desc("id")); // 添加降序排序条件
        return reservationMapper.selectPageWithPhysicalReservation(page,null);
    }

    //通过id修改信息
    @PutMapping("/reservation/uid/{uid}")
    public Reservation updateReservation(@PathVariable("uid") int id, @RequestBody Reservation reservation){
        Reservation exist = reservationMapper.selectById(id);
        if(exist != null){
            reservation.setId(id);
            reservationMapper.updateById(reservation);
            return reservation;
        }else{
            return null;
        }
    }

}
