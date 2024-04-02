package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

//    @Query("SELECT a FROM Author a WHERE a.age > ?1")
//    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}
