package com.example.myapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new EntityNotFoundException("Book not found for id: " + id);
        }
        return book;
    }

    @Override
    @Transactional // Zapewnia, że metoda jest wykonywana w kontekście transakcji
    public void addBook(Book book) {
        bookRepository.save(book); // Zapisuje książkę do bazy
    }

    @Override
    @Transactional
    public void updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBookOptional = bookRepository.findById(id);

        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setYear(updatedBook.getYear());
            bookRepository.save(existingBook); // Zapisuje zaktualizowaną książkę do bazy danych
        } else {
            throw new ResourceNotFoundException("Book not found with id " + id);
        }
    }

    @Override
    @Transactional
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) { // Sprawdza, czy książka istnieje w bazie danych
            bookRepository.deleteById(id); // Usuwa książkę
            return true;
        } else {
            return false; // Zwraca false, jeśli książki nie było w bazie danych
        }
    }
}
