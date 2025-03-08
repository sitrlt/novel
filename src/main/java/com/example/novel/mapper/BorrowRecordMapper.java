package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
    @Select("SELECT br.*, b.* " +
            "FROM borrow_record br " +
            "JOIN book b ON br.book_isbn = b.isbn " +
            "WHERE br.reader_id = #{readerId}")
    @Results({
            @Result(property = "borrowRecord.id", column = "id"),
            @Result(property = "borrowRecord.readerId", column = "reader_id"),
            @Result(property = "borrowRecord.bookIsbn", column = "book_isbn"),
            @Result(property = "borrowRecord.dueDate", column = "due_date"),
            @Result(property = "borrowRecord.returnDate", column = "return_date"),
            @Result(property = "borrowRecord.borrowDate", column = "borrow_date"),
            @Result(property = "borrowRecord.status", column = "status"),
            // 这里需要根据 borrow_record 表的实际列名和 BorrowRecord 类的属性名进行补充
            @Result(property = "book.id", column = "id"),
            @Result(property = "book.title", column = "title"),
            @Result(property = "book.author", column = "author"),
            @Result(property = "book.isbn", column = "isbn"),
            @Result(property = "book.publisherId", column = "publisher_id"),
            @Result(property = "book.description", column = "description"),
            @Result(property = "book.coverImage", column = "cover_image"),
            @Result(property = "book.publishDate", column = "publish_date"),
            @Result(property = "book.stockQuantity", column = "stock_quantity"),
            @Result(property = "book.ebookPath", column = "ebook_path"),
            // 这里需要根据 book 表的实际列名和 Book 类的属性名进行补充
    })
    List<BorrowRecordDetail> selectByReaderId(@Param("readerId") Integer readerId);

    @Insert("INSERT INTO borrow_record (reader_id, book_isbn, due_date, return_date, borrow_date, fine,status) " +
            "VALUES (#{readerId}, #{bookIsbn}, #{dueDate}, #{returnDate}, #{borrowDate}, #{fine},#{status})")
    void insertBorrowRecord(BorrowRecord borrowRecord);


    @Select("select * from `borrow_record`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_id", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectById")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<BorrowRecord> selectAllBorrowRecords();
    @Select("select * from `borrow_record`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))

    })
    IPage<BorrowRecord> selectPageWithBorrowRecord(Page<?> page, @Param("queryWrapper") Wrapper<BorrowRecord> queryWrapper);


    @Select("select * from `borrow_record`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<BorrowRecord> selectList(@Param("ew") Wrapper<BorrowRecord> queryWrapper);

    @Select("SELECT br.* " +
            "FROM borrow_record br " +
            "JOIN book b ON br.book_isbn = b.isbn " +
            "JOIN reader r ON br.reader_id = r.id " +
            "WHERE b.title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR r.username LIKE CONCAT('%', #{keyword}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "fine", column = "fine"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(property = "reader", column = "reader_id", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<BorrowRecord> searchBorrowRecordsByKeyword(@Param("keyword") String keyword);

    @Update("UPDATE borrow_record SET status = #{status} WHERE id = #{id}")
    int updateBorrowRecordStatus(BorrowRecord borrowRecord);

    /**
     * 获取所有书籍的 ISBN 和对应的借阅数量
     * @return 包含书籍 ISBN 和借阅数量的 Map 列表
     */
    @Select("SELECT b.book_isbn, COUNT(*) AS borrow_count FROM borrow_record b GROUP BY b.book_isbn ORDER BY borrow_count DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "fine", column = "fine"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(property = "reader", column = "reader_id", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn")),
            @Result(column = "id", property = "labels", javaType = List.class,
                    many = @Many(select = "com.example.novel.mapper.LabelMapper.selectLabelsByBookId")),
    })
    List<Map<String, Object>> getBooksBorrowCount();
}