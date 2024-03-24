package com.homeprojects.springjdbc.dao;

import com.homeprojects.springjdbc.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(long l);
}
