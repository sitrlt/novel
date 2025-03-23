package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Publisher;
import com.example.novel.pojo.PublisherSales;
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
    @Select("SELECT p.name, SUM(br.borrowing_fee) AS totalSales " +
            "FROM borrow_record br " +
            "JOIN book b ON br.book_isbn = b.isbn " +
            "JOIN publisher p ON b.publisher_id = p.id " +
            "WHERE p.id = #{id} " +
            "GROUP BY p.name")
    PublisherSales getPublisherSales(@Param("id") int id);


}
