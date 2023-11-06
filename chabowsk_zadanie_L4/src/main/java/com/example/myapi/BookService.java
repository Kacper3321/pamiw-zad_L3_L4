package com.example.myapi;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    void addBook(Book book);

    void updateBook(Long id, Book book);

    boolean deleteBook(Long id);
}
