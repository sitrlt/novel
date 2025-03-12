package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.Librarian;
import com.example.novel.pojo.Reader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LibrarianMapper extends BaseMapper<Librarian> {
    @Select("SELECT * FROM librarian WHERE employee_id = #{employeeId}")
    Librarian selectByEmployeeId(@Param("employeeId") String id);
    @Select("select * from `librarian`")
    List<Librarian> selectAlllibrarians();
}
