/*
package com.example.novel;

import com.example.novel.pojo.Book;
import com.example.novel.pojo.Interests;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Reader;

import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 基于内容的推荐器
class ContentBasedRecommender {
    public List<Book> recommendBooks(Reader reader, List<Book> allBooks) {
        List<Book> recommendedBooks = new ArrayList<>();
        // 遍历所有书籍
        for (Book book : allBooks) {
            int matchCount = 0;
            // 遍历书籍的标签
            for (Label bookLabel : book.getLabels()) {
                // 遍历读者的兴趣标签
                for (Label readerLabel : reader.getInterests()) {
                    if (bookLabel.getLabel().equals(readerLabel.getLabel())) {
                        matchCount++;
                    }
                }
            }
            // 如果有匹配的标签，则将该书加入推荐列表
            if (matchCount > 0) {
                recommendedBooks.add(book);
            }
        }
        // 按匹配标签数量降序排序推荐列表
        recommendedBooks.sort((b1, b2) -> {
            int count1 = getMatchCount(b1, reader);
            int count2 = getMatchCount(b2, reader);
            return count2 - count1;
        });
        return recommendedBooks;
    }

    private int getMatchCount(Book book, Reader reader) {
        int matchCount = 0;
        for (Label bookLabel : book.getLabels()) {
            for (Label readerLabel : reader.getLabels()) {
                if (bookLabel.getLabel().equals(readerLabel.getLabel())) {
                    matchCount++;
                }
            }
        }
        return matchCount;
    }
}
// 测试代码
public class test1 {
    public static void main(String[] args) {
        // 初始化标签和兴趣
        Label label1 = new Label();
        label1.setLabel("Java");
        Label label2 = new Label();
        label2.setLabel("编程");
        Label label3 = new Label();
        label3.setLabel("文学");
        Label label4 = new Label();
        label4.setLabel("古典");

        // 初始化书籍
        List<Book> allBooks = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Java编程思想");
        book1.setLabels(Arrays.asList(label1, label2));
        allBooks.add(book1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("红楼梦");
        book2.setLabels(Arrays.asList(label3, label4));
        allBooks.add(book2);

        // 初始化读者
        Reader reader = new Reader();
        reader.setId(1);
        reader.setUsername("testUser");
        reader.setLabels(Arrays.asList(label1, label2));

        // 创建推荐器
        ContentBasedRecommender recommender = new ContentBasedRecommender();

        // 进行推荐
        List<Book> recommendedBooks = recommender.recommendBooks(reader, allBooks);

        // 输出推荐结果
        System.out.println("为读者推荐的书籍：");
        for (Book book : recommendedBooks) {
            System.out.println(book.getTitle());
        }
    }
}*/
