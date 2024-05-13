package com.homeprojects.springjdbc.services.impl;

import com.homeprojects.springjdbc.domain.BookEntity;
import com.homeprojects.springjdbc.domain.dto.BookDto;
import com.homeprojects.springjdbc.repositories.BookRepository;
import com.homeprojects.springjdbc.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity saveBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        bookEntity = bookRepository.save(bookEntity);
        return bookEntity;
    }

    @Override
    public List<BookEntity> findAll() {

        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }


    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public BookEntity getBook(String id) {
        return bookRepository.findById(id).isPresent()? bookRepository.findById(id).get():null;
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity updateBook(BookEntity bookEntity) {
        Optional<BookEntity> optEntityInDb = bookRepository.findById(bookEntity.getIsbn());
        return optEntityInDb.map(entityInDb ->{
                Optional.ofNullable(bookEntity.getTitle()).ifPresent(entityInDb::setTitle);
                Optional.ofNullable(bookEntity.getAuthorEntity()).ifPresent(entityInDb::setAuthorEntity);
                return saveBook(entityInDb.getIsbn(), entityInDb);
        })
                .orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
