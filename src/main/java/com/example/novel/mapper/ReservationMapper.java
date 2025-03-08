package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

    @Select("SELECT r.*, b.* " +
            "FROM reservation r " +
            "JOIN book b ON r.book_isbn = b.isbn " +
            "WHERE r.reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "status", column = "status"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<Reservation> selectByReaderId(@Param("readerId") Integer readerId);

    /**
     * 实体书借阅查询
     */
    @Select("SELECT * from reservation")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "status", column = "status"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<Reservation> selectAllPhysicalReservations();
    @Select("SELECT * from reservation")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "adminResponse", column = "admin_response"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    IPage<Reservation> selectPageWithPhysicalReservation(Page<?> page, @Param("queryWrapper") Wrapper<Reservation> queryWrapper);


}
