package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.BookChapter;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookChapterMapper extends BaseMapper<BookChapter> {
    @Select("select * from book_chapter where book_id = #{bookId}")
    List<BookChapter> getBookChapterByBookId(@Param("bookId") int bookId);

}
