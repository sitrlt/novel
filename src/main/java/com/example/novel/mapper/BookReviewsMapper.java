package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.novel.pojo.BookReviews;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Publisher;
import com.example.novel.pojo.Reader;
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
    @Select("SELECT COUNT(*) FROM book_reviews WHERE book_id = #{bookId}")
    int countByBookId(@Param("bookId") int bookId);
    @Insert("INSERT INTO book_reviews (reader_id, book_id, review_text, rating, review_date)\n" +
            "VALUES (#{readerId}, #{bookId}, #{reviewText}, #{rating}, #{reviewDate})")
    void insertBookReview(BookReviews bookReviews);
}
