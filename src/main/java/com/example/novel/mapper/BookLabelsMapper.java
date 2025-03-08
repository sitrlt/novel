package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.BookLabels;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookLabelsMapper extends BaseMapper<BookLabels> {
    @Select("select * from book_labels where book_id = #{bookId}")
    List<BookLabels> findByBookId(int bookId);
}
