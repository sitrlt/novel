package com.example.novel.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Reader {
   private int id;
   private String username;
   private String password;
   private String email;
   private String phone;
   private String address;
   @TableField("registration_date")
   private String registrationDate;
   private String preferences;
   @TableField("cover_image")
   private String coverImage;
   @TableField(exist = false)
   private List<Book> books; // Publisher 对象
   @TableField(exist = false)
   private List<Label> interests;
}
