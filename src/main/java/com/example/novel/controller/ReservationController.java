package com.example.novel.controller;


import com.example.novel.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationMapper reservationMapper;

}
