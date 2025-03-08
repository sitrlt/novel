package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
    // 使用 @Insert 注解编写插入语句
    @Insert("INSERT INTO feedback (reader_id, content, admin_response, response_date,feedback_type,target_entity) " +
            "VALUES (#{readerId}, #{content}, #{adminResponse}, #{responseDate},#{feedbackType},#{targetEntity})")
    void insertFeedback(Feedback feedback);
    @Select("select * from `feedback`  where target_entity = 'admin'")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "status", column = "status"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(property = "targetEntity", column = "target_entity"),
            @Result(property = "feedbackType", column = "feedback_type"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),

    })
    List<Feedback> selectAllFeedBack();
    @Select("select * from `feedback` where target_entity = 'admin'")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "status", column = "status"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(property = "targetEntity", column = "target_entity"),
            @Result(property = "feedbackType", column = "feedback_type"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
    })
    IPage<Feedback> selectPageWithFeedback(Page<?> page, @Param("queryWrapper") Wrapper<Feedback> queryWrapper);
    @Select("select * from feedback where reader_id = #{readerId}")
    List<Feedback> selectFeedbackByReaderId(@Param("readerId") int readerId);
    @Delete("DELETE FROM feedback WHERE reader_id = #{readerId}")
    void deleteFeedbacksByReaderId(@Param("readerId") Long readerId);


    @Select("select * from `feedback` where target_entity = 'publisher'")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "status", column = "status"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(property = "targetEntity", column = "target_entity"),
            @Result(property = "feedbackType", column = "feedback_type"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
    })
    List<Feedback> selectAllFeedBackToPublisher();

    @Select("select * from `feedback` where target_entity = 'publisher'")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "status", column = "status"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(property = "targetEntity", column = "target_entity"),
            @Result(property = "feedbackType", column = "feedback_type"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
    })
    IPage<Feedback> selectPageToPublisher(Page<?> page, @Param("queryWrapper") Wrapper<Feedback> queryWrapper);

}
