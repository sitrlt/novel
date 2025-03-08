package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.BookInventory;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Publisher;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("select * from `book` where id = #{id}")
    Book selectById(int id);

    @Select("SELECT COUNT(*) FROM book")
    int getTotalBookCount();

    @Select("SELECT COUNT(*) FROM borrow_record WHERE status = '借阅中'")
    int getBorrowingBookCount();

    @Select("SELECT COUNT(DISTINCT reader_id) FROM borrow_record WHERE status = '借阅中'")
    int getBorrowingPersonCount();

    @Select("SELECT COUNT(*) FROM borrow_record WHERE status = '已归还'")
    int getReturnedBookCount();

    @Select("SELECT COUNT(DISTINCT reader_id) FROM borrow_record WHERE status = '已归还'")
    int getReturnedPersonCount();

    @Select("SELECT COUNT(*) FROM borrow_record WHERE status = '已逾期'")
    int getOverdueBookCount();

    @Select("SELECT COUNT(DISTINCT reader_id) FROM borrow_record WHERE status = '已逾期'")
    int getOverduePersonCount();

    @Select("SELECT DISTINCT * FROM `book` WHERE isbn = #{isbn}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "ebookPath", column = "ebook_path"),
            @Result(property = "isEbook", column = "is_ebook"),
            @Result(column = "publisher_id", property = "publisher", javaType = Publisher.class,
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
            @Result(column = "isbn",property = "bookInventory",javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByIsbn"))
    })
    Book selectByIsbn(String isbn);
    @Select("select * from `book`")
    public List<Book> selectAllUserAndOrders();
    @Select("SELECT COUNT(*) FROM book WHERE id = #{id}")
    int existsById(@Param("id") int id);

    @Select("select * from `book` WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "ebookPath", column = "ebook_path"),
            @Result(property = "isEbook", column = "is_ebook"),
            @Result(column = "publisher_id", property = "publisher", javaType = Publisher.class,
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
            @Result(property = "bookInventory", column = "isbn",javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByIsbn"))
    })
    Book selectBookById(@Param("id") int id);
    @Select("SELECT * FROM book")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "ebookPath", column = "ebook_path"),
            @Result(property = "isEbook", column = "is_ebook"),
            @Result(column = "publisher_id", property = "publisher", javaType = Publisher.class,
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
            @Result(column = "isbn",property = "bookInventory",javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByIsbn"))
    })
    List<Book> selectAllBookAndPublisher();
    @Select("SELECT * FROM book")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "ebookPath", column = "ebook_path"),
            @Result(property = "isEbook", column = "is_ebook"),
            @Result(column = "publisher_id", property = "publisher", javaType = Publisher.class,
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
            @Result(property = "bookInventory", column = "isbn",
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByIsbn"))
    })
    IPage<Book> selectPageWithPublisher(Page<?> page, @Param("queryWrapper") Wrapper<Book> queryWrapper);

    @Select("SELECT * FROM book where publisher_id = #{publisherId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "ebookPath", column = "ebook_path"),
            @Result(property = "isEbook", column = "is_ebook"),
            @Result(column = "publisher_id", property = "publisher", javaType = Publisher.class,
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
            @Result(property = "bookInventory", column = "isbn",
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByIsbn"))
    })
    List<Book> selectAllBookToPublisher(@Param("publisherId") int publisherId);
    @Select("SELECT * FROM book where publisher_id = #{publisherId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "description", column = "description"),
            @Result(property = "coverImage", column = "cover_image"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "ebookPath", column = "ebook_path"),
            @Result(property = "isEbook", column = "is_ebook"),
            @Result(column = "publisher_id", property = "publisher", javaType = Publisher.class,
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
            @Result(property = "bookInventory", column = "isbn",
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByIsbn"))
    })
    IPage<Book> selectPageToPublisher(Page<Book> page, @Param("publisherId") Integer publisherId);

    @Select("SELECT b.* " +
            "FROM book b " +
            "JOIN publisher p ON b.publisher_id = p.id " +  // 注意这里的空格
            "WHERE b.title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR b.author LIKE CONCAT('%', #{keyword}, '%') " +
            "OR b.description LIKE CONCAT('%', #{keyword}, '%') " +  // 注意这里的空格
            "OR p.name LIKE CONCAT('%', #{keyword}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "isbn", column = "isbn"),
            @Result(property = "title", column = "title"),
            @Result(property = "author", column = "author"),
            @Result(property = "description", column = "description"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "publisher", column = "publisher_id",
                    one = @One(select = "com.example.novel.mapper.PublisherMapper.selectById"))
    })
    List<Book> searchBooks(@Param("keyword") String keyword);


    @Select("SELECT * FROM book WHERE title LIKE CONCAT('%', #{keyword}, '%') OR author LIKE CONCAT('%', #{keyword}, '%')")
    IPage<Book> searchBooks1(Page<Book> page, @Param("keyword") String keyword);
}
