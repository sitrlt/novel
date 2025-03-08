package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Publisher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    @Select("select * from `label`")
    List<Label> selectAllLabel();
    @Select("SELECT COUNT(*) FROM label WHERE id = #{id}")
    int existsById(@Param("id") int id);
    @Select("select * from `label` where id = #{id}")
    Label selectById(int id);
    @Select("SELECT l.* FROM label l " +
            "JOIN book_labels bl ON l.id = bl.label_id " +
            "WHERE bl.book_id = #{bookId}")
    List<Label> selectLabelsByBookId(int bookId);
    @Select("SELECT l.* FROM label l " +
            "JOIN interests i ON l.id = i.label_id " +
            "WHERE i.reader_id = #{readerId}")
    List<Label> selectLabelsByReaderId(int reader);
}
