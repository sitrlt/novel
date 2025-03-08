package com.example.novel.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.BookLabels;
import com.example.novel.pojo.Interests;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InterestsMapper extends BaseMapper<Interests> {
    @Select("select * from interests where reader_id = #{readerId}")
    List<Interests> findByReaderId(int readerId);

    @Delete("DELETE FROM interests WHERE label_id = #{labelId}")
    int deleteInterests(@Param("labelId") int labelId);
    @Select("SELECT COUNT(*) FROM interests WHERE label_id = #{labelId}")
    int existsById(@Param("labelId") int labelId);
}
