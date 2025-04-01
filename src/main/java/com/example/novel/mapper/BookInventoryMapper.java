package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.BookInventory;
import com.example.novel.pojo.Publisher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookInventoryMapper extends BaseMapper<BookInventory> {
    @Select("SELECT * FROM book_inventory where publisher_id = #{publisherId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "totalCopies", column = "total_copies"),
            @Result(property = "availableCopies", column = "available_copies"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectById")),
    })
    List<BookInventory> selectAllBookInventory(@Param("publisherId") int publisherId);

    @Select("SELECT * FROM book_inventory where publisher_id = #{publisherId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "totalCopies", column = "total_copies"),
            @Result(property = "availableCopies", column = "available_copies"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn"))
    })
    IPage<BookInventory> selectPageWithPublisher(Page<BookInventory> page, @Param("publisherId") Integer publisherId);


    @Select("SELECT DISTINCT * FROM book_inventory WHERE book_isbn = #{isbn}")
    BookInventory selectByIsbn(String isbn);

    @Select("SELECT DISTINCT * FROM book_inventory WHERE book_isbn = #{bookIsbn}")
    BookInventory selectByBookIsbn(String bookIsbn);

    @Update("UPDATE book_inventory SET available_copies = available_copies - 1 WHERE book_isbn = #{bookIsbn}")
    int decreaseAvailableCount(@Param("bookIsbn") String bookIsbn);

    @Update("UPDATE book_inventory SET available_copies = available_copies + 1 WHERE book_isbn = #{bookIsbn}")
    int creaseAvailableCount(@Param("bookIsbn") String bookIsbn);


    @Select("SELECT available_copies FROM book_inventory WHERE book_isbn = #{bookIsbn}")
    Integer getAvailableCountByIsbn(@Param("bookIsbn") String bookIsbn);


    @Select("SELECT pi.* " +
            "FROM book_inventory pi " +
            "JOIN book b ON b.isbn = pi.book_isbn " +  // 注意这里的空格
            "WHERE b.title LIKE CONCAT('%', #{keyword}, '%') ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "totalCopies", column = "total_copies"),
            @Result(property = "availableCopies", column = "available_copies"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
    })
    List<BookInventory> searchBookInventory(@Param("keyword") String keyword);
}