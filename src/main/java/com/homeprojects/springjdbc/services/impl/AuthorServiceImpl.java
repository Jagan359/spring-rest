package com.homeprojects.springjdbc.services.impl;

import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.repositories.AuthorRepository;
import com.homeprojects.springjdbc.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
