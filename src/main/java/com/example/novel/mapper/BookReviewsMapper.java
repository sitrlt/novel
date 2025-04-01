package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookReviewsMapper extends BaseMapper<BookReviews> {
    @Select("select * from book_reviews where book_id = #{bookId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "readText", column = "read_text"),
            @Result(property = "reviewDate", column = "review_date"),
            @Result(property = "rating", column = "rating"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById"))
    })
    List<BookReviews> selectByBookId(@Param("bookId") int bookId);

    @Select("select * from book_reviews where book_id = #{bookId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "readText", column = "read_text"),
            @Result(property = "reviewDate", column = "review_date"),
            @Result(property = "rating", column = "rating"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_id", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectById"))
    })
    IPage<BookReviews> selectPageWithBookReviewsByBookId(Page<BookReviews> page, @Param("bookId") int bookId);

    @Select("SELECT COUNT(*) FROM book_reviews WHERE book_id = #{bookId}")
    int countByBookId(@Param("bookId") int bookId);
    @Insert("INSERT INTO book_reviews (reader_id, book_id, review_text, rating, review_date)\n" +
            "VALUES (#{readerId}, #{bookId}, #{reviewText}, #{rating}, #{reviewDate})")
    void insertBookReview(BookReviews bookReviews);
    @Select("select * from book_reviews where reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "readText", column = "read_text"),
            @Result(property = "reviewDate", column = "review_date"),
            @Result(property = "rating", column = "rating"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_id", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectById"))
    })
    List<BookReviews> selectByReaderId(@Param("readerId") int readerId);

    @Select("SELECT * FROM book_reviews where reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "readText", column = "read_text"),
            @Result(property = "reviewDate", column = "review_date"),
            @Result(property = "rating", column = "rating"),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_id", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectById"))
    })
    IPage<BookReviews> selectPageWithBookReviews(Page<BookReviews> page, @Param("readerId") Integer readerId);


}
