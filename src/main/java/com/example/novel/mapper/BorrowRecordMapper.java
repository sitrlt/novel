package com.example.novel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.novel.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
    @Select("SELECT * from borrow_record WHERE reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<BorrowRecord> selectByReaderId(@Param("readerId") Integer readerId);

    @Insert("INSERT INTO borrow_record (reader_id, book_isbn, due_date, return_date, borrow_date, fine,status,borrowing_fee) " +
            "VALUES (#{readerId}, #{bookIsbn}, #{dueDate}, #{returnDate}, #{borrowDate}, #{fine},#{status},#{borrowingFee})")
    int insertBorrowRecord(BorrowRecord borrowRecord);


    /**
     * 实体书借阅查询
     */
    @Select("SELECT br.* FROM `borrow_record` br " +
            "JOIN `book` b ON br.`book_isbn` = b.`isbn` " +
            "WHERE b.`is_ebook` = false ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<BorrowRecord> selectAllPhysicalBorrowRecords();
    @Select("SELECT br.* FROM `borrow_record` br " +
            "JOIN `book` b ON br.`book_isbn` = b.`isbn` " +
            "WHERE b.`is_ebook` = false ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))

    })
    IPage<BorrowRecord> selectPageWithPhysicalBorrowRecord(Page<?> page, @Param("queryWrapper") Wrapper<BorrowRecord> queryWrapper);

    /**
     * 电子书借阅查询
     */
    @Select("SELECT br.* FROM `borrow_record` br " +
            "JOIN `book` b ON br.`book_isbn` = b.`isbn` " +
            "WHERE b.`is_ebook` = true ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))
    })
    List<BorrowRecord> selectAllEbookBorrowRecords();
    @Select("SELECT br.* FROM `borrow_record` br " +
            "JOIN `book` b ON br.`book_isbn` = b.`isbn` " +
            "WHERE b.`is_ebook` = true ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById")),
            @Result(column = "book_isbn", property = "bookInventory", javaType = BookInventory.class,
                    one = @One(select = "com.example.novel.mapper.BookInventoryMapper.selectByBookIsbn"))

    })
    IPage<BorrowRecord> selectPageWithEbookBorrowRecord(Page<?> page, @Param("queryWrapper") Wrapper<BorrowRecord> queryWrapper);


    @Select("SELECT * FROM borrow_record where reader_id = #{readerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "status", column = "status"),
            @Result(column = "book_isbn", property = "book", javaType = Book.class,
                    one = @One(select = "com.example.novel.mapper.BookMapper.selectByIsbn")),
            @Result(column = "reader_id", property = "reader", javaType = Reader.class,
                    one = @One(select = "com.example.novel.mapper.ReaderMapper.selectById"))
    })
    IPage<BorrowRecord> selectPage1(Page<BorrowRecord> page, @Param("readerId") Integer readerId);


    @Select("select * from `borrow_record`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "readerId", column = "reader_id"),
            @Result(property = "bookIsbn", column = "book_isbn"),
            @Result(property = "dueDate", column = "due_date"),
            @Result(property = "returnDate", column = "return_date"),
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "borrowingFee", column = "borrowing_fee"),
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
            @Result(property = "borrowingFee", column = "borrowing_fee"),
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

    @Update("UPDATE borrow_record SET status = #{status}, WHERE id = #{id}")
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
            @Result(property = "borrowingFee", column = "borrowing_fee"),
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


    // 统计不同书籍的借阅次数
    @Select("SELECT b.title, COUNT(br.id) as borrowCount FROM borrow_record br " +
            "JOIN book b ON br.book_isbn = b.isbn GROUP BY b.title")
    List<Map<String, Object>> countBorrowsByBookTitle();

    // 统计不同读者的借阅次数
    @Select("SELECT COALESCE(r.username, 'Unknown User') as username, COUNT(br.id) as borrowCount " +
            "FROM borrow_record br " +
            "JOIN reader r ON br.reader_id = r.id " +
            "GROUP BY COALESCE(r.username, 'Unknown User')")
    List<Map<String, Object>> countBorrowsByReaderName();

    // 统计每日的借阅数量
    @Select("SELECT DATE(br.borrow_date) as borrowDate, COUNT(br.id) as borrowCount FROM borrow_record br " +
            "GROUP BY DATE(br.borrow_date)")
    List<Map<String, Object>> countBorrowsByDay();

    @Select({
            "SELECT l.label, COUNT(br.book_isbn) AS borrow_count",
            "FROM borrow_record br",
            "JOIN book b ON br.book_isbn = b.isbn",
            "JOIN book_labels bl ON b.id = bl.book_id",
            "JOIN label l ON bl.label_id = l.id",
            "JOIN publisher p ON b.publisher_id = p.id",
            "WHERE p.id = #{id}",
            "GROUP BY l.label"
    })
    List<Map<String, Object>> countBorrowsByPublisherId(@Param("id") int id);

    @Select({
            "SELECT DATE(br.borrow_date) AS borrow_date, ",
            "       SUM(br.borrowing_fee) AS daily_sales ",
            "FROM borrow_record br ",
            "JOIN book b ON br.book_isbn = b.isbn ",
            "JOIN publisher p ON b.publisher_id = p.id ",
            "WHERE p.id = #{id} ",
            "GROUP BY DATE(br.borrow_date)"
    })
    List<Map<String, Object>> getDailySales(@Param("id") int id);
}