package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReaderMapper extends BaseMapper<Reader> {
    @Select("select * from `reader`")
    List<Reader> selectAllUserAndOrders();
    @Select("SELECT COUNT(*) FROM reader WHERE id = #{id}")
    int existsById(@Param("id") int id);
    @Select("SELECT * FROM reader WHERE phone = #{phone}")
    Reader selectByPhone(@Param("phone") String phone);
    @Update("UPDATE reader SET cover_image = #{coverImage} WHERE id = #{id}")
    void updateCoverImagePath(Long id, String coverImagePath);
    @Insert("INSERT INTO reader (username, password, phone, cover_image) " +
            "VALUES (#{username}, #{password}, #{phone}, #{coverImage});")
    void insertReader(@Param("username") String username,
                      @Param("password") String password,
                      @Param("phone") String phone,
                      @Param("coverImage") String coverImage);
    @Update("UPDATE reader SET ${field} = #{value} WHERE id = #{id}")
    int updateReader(@Param("field") String field, @Param("value") String value, @Param("id") int id);
    @Update("UPDATE reader SET cover_image = #{coverImage} WHERE id = #{id}")
    int updateReaderImage(@Param("coverImage") String coverImage, @Param("id") int id);


    @Select("select * from reader where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "address", column = "address"),
            @Result(property = "accountBalance", column = "account_balance"),
            @Result(property = "registrationDate", column = "registration_date"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(column = "id", property = "interests", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByReaderId"))
    })
    Reader selectById(@Param("id") int id);
}
