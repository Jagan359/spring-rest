package com.homeprojects.springjdbc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.domain.dto.AuthorDto;
import com.homeprojects.springjdbc.mappers.Mapper;
import com.homeprojects.springjdbc.services.AuthorService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log
public class AuthorController {
    AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;
    private ObjectMapper objectMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper, ObjectMapper objectMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.objectMapper = objectMapper;

    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> getAuthors(){
        List<AuthorEntity> authorEntities = authorService.findAll();
        return authorEntities.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/authorname/{name}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("name") String name) throws JsonProcessingException {
        AuthorEntity authorEntity = authorService.findByName(name);
        log.info("Author Entity return is : " + objectMapper.writeValueAsString(authorEntity));
        return authorEntity!=null? new ResponseEntity<>(authorMapper.mapTo(authorEntity), HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) throws JsonProcessingException {
        AuthorEntity authorEntity = authorService.findById(id);
//        JSONObject jsonObject = authorEntity!=null? new JSONObject(objectMapper.writeValueAsString(authorMapper.mapTo(authorEntity)));
        log.info("Author Entity return is : " + objectMapper.writeValueAsString(authorEntity));
        return authorEntity!=null ?
                new ResponseEntity<>(authorMapper.mapTo(authorEntity),
                        HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthorDto(@PathVariable Long id, @RequestBody AuthorDto authorDto){

        authorDto.setId(id);
        if (!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        authorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>( authorMapper.mapTo(authorEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthorDto(@PathVariable Long id){
        authorService.delete(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto){
        authorDto.setId(id);
        if (!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        authorEntity = authorService.update(authorEntity);
        return new ResponseEntity<>( authorMapper.mapTo(authorEntity), HttpStatus.OK);
    }
}
