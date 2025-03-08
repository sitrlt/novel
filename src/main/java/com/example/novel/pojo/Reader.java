package com.example.novel.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.text.SimpleDateFormat;
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
   private java.sql.Timestamp registrationDate;
   @TableField("cover_image")
   private String coverImage;
   @TableField(exist = false)
   private List<Book> books; // Publisher 对象
   @TableField(exist = false)
   private List<Label> interests;
   // 自定义 getter 方法，将 Timestamp 转换为日期字符串
   public String getRegistrationDateStr() {
      if (registrationDate != null) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return sdf.format(registrationDate);
      }
      return null;
   }
}
