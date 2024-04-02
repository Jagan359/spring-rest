package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
}
