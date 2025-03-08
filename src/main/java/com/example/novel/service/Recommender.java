package com.example.novel.service;

import com.example.novel.mapper.ReaderMapper;
import com.example.novel.pojo.Book;
import com.example.novel.pojo.Label;
import com.example.novel.pojo.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Recommender {
    @Autowired
    private ReaderMapper readerMapper;
    public List<Book> recommendBooks(int userId, List<Book> allBooks) {
        Reader reader = readerMapper.selectById(userId);
        if (reader == null) {
            return Collections.emptyList();
        }

        List<Label> readerInterests = reader.getInterests();
        List<Book> recommendedBooks = new ArrayList<>();

        for (Book book : allBooks) {
            List<Label> bookLabels = book.getLabels();
            boolean match = bookLabels.stream().anyMatch(readerInterests::contains);
            if (match) {
                recommendedBooks.add(book);
            }
        }

        return recommendedBooks;
    }
}