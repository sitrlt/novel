package com.example.novel.service;

import com.example.novel.pojo.Book;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Reader;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentBasedRecommender {
    /**
     * 根据读者的兴趣标签推荐书籍
     *
     * @param reader 读者对象，包含其兴趣标签
     * @param allBooks 所有书籍列表
     * @return 推荐的书籍列表，按匹配标签数量降序排序
     */
    public List<Book> recommendBooks(Reader reader, List<Book> allBooks) {
        // 提取读者的兴趣标签，确保标签不为空
        Set<String> readerLabels = new HashSet<>();
        for (Label readerLabel : reader.getInterests()) {
            if (readerLabel != null && readerLabel.getLabel() != null) {
                readerLabels.add(readerLabel.getLabel().toLowerCase()); // 统一转为小写
            }
        }

        // 如果读者没有兴趣标签，直接返回空列表
        if (readerLabels.isEmpty()) {
            return Collections.emptyList();
        }

        // 存储匹配的书籍及其匹配标签数量
        Map<Book, Integer> bookMatchCountMap = new HashMap<>();

        // 遍历所有书籍，计算每本书与读者兴趣标签的匹配数量
        for (Book book : allBooks) {
            int matchCount = getMatchCount(book, readerLabels);
            if (matchCount > 0) {
                bookMatchCountMap.put(book, matchCount);
            }
        }

        // 将匹配的书籍按匹配数量降序排序
        List<Book> recommendedBooks = new ArrayList<>(bookMatchCountMap.keySet());
        recommendedBooks.sort((b1, b2) -> {
            int count1 = bookMatchCountMap.get(b1);
            int count2 = bookMatchCountMap.get(b2);
            return Integer.compare(count2, count1); // 降序排序
        });

        return recommendedBooks;
    }

    /**
     * 计算书籍与读者兴趣标签的匹配数量
     *
     * @param book 书籍对象
     * @param readerLabels 读者的兴趣标签集合
     * @return 匹配的标签数量
     */
    private int getMatchCount(Book book, Set<String> readerLabels) {
        int matchCount = 0;
        for (Label bookLabel : book.getLabels()) {
            if (bookLabel != null && bookLabel.getLabel() != null &&
                    readerLabels.contains(bookLabel.getLabel().toLowerCase())) { // 统一转为小写
                matchCount++;
            }
        }
        return matchCount;
    }
}