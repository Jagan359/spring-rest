package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("SELECT a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);

    @Query("SELECT a from AuthorEntity a where a.name = ?1")
    Iterable<AuthorEntity> findByName(String name);
}
