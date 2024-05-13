package com.homeprojects.springjdbc.services;

import com.homeprojects.springjdbc.domain.BookEntity;
import com.homeprojects.springjdbc.domain.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookEntity saveBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Page<BookEntity> findAll(Pageable pageable);

    BookEntity getBook(String id);

    boolean isExists(String isbn);

    BookEntity updateBook(BookEntity bookEntity);

    void delete(String isbn);
}
