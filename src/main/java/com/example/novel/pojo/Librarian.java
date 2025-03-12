package com.example.novel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Librarian {
    private int id;
    private String name;
    @TableField("employee_id")
    private String employeeId;
    private String password;
    private String email;

}
