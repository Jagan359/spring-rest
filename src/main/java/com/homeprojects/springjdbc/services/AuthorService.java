package com.homeprojects.springjdbc.services;

import com.homeprojects.springjdbc.domain.AuthorEntity;

import java.util.List;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();


    AuthorEntity findById(Long id);

    AuthorEntity findByName(String name);

    boolean isExists(Long id);

    AuthorEntity update(AuthorEntity authorEntity);

    void delete(Long id);
}
