package com.homeprojects.springjdbc.services.impl;

import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.repositories.AuthorRepository;
import com.homeprojects.springjdbc.services.AuthorService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return (List<AuthorEntity>) authorRepository.findAll();
    }

    @Override
    public AuthorEntity findByName(String name) {
        Optional<AuthorEntity> authorEntity = StreamSupport.stream(authorRepository.findByName(name).spliterator(), false).findFirst();
        return authorEntity.isPresent()?authorEntity.get():null;
    }

    @Override
    public boolean isExists(Long id) {
        return findById(id)!=null;
    }

    @Override
    public AuthorEntity update(AuthorEntity authorEntity) {
        Optional<AuthorEntity> optionalAuthorEntityFromDb= authorRepository.findById(authorEntity.getId());
        return optionalAuthorEntityFromDb
                .map((authorEntityInDb) ->{
                    Optional.ofNullable(authorEntity.getName()).ifPresent(authorEntityInDb::setName);
                    Optional.ofNullable(authorEntity.getAge()).ifPresent(authorEntityInDb::setAge);
                    return authorRepository.save(authorEntityInDb);
                })
                .orElseThrow(()-> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorEntity findById(Long id) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        return authorEntity.orElse(null);
    }
}
