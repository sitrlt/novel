package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Publisher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PublisherMapper extends BaseMapper<Publisher> {
    @Select("select * from `publisher` where id = #{id}")
    Publisher selectById(int id);
    @Select("SELECT * FROM publisher WHERE phone = #{phone}")
    Publisher selectByPhone(@Param("phone") String phone);
    @Select("select * from `publisher`")
    List<Publisher> selectAllPublisher();
}
