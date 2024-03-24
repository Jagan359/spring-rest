package com.homeprojects.springjdbc.dao;

import com.homeprojects.springjdbc.domain.Book;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);
}
