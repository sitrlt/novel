package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Publisher {
    private int id;
    private String name;
    @TableField("contact_person")
    private String contactPerson;
    private String email;
    private String password;
    private String phone;
}
