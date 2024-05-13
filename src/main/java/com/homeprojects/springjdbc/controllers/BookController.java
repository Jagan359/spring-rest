package com.homeprojects.springjdbc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeprojects.springjdbc.domain.BookEntity;
import com.homeprojects.springjdbc.domain.dto.BookDto;
import com.homeprojects.springjdbc.mappers.impl.BookMapper;
import com.homeprojects.springjdbc.services.BookService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@Log
public class BookController {

    private BookMapper bookMapper;
    private ObjectMapper objectMapper;
    private BookService bookService;

    public BookController(BookMapper bookMapper, BookService bookService, ObjectMapper objectMapper){
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.objectMapper = objectMapper;

    }
    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn,
                                              @RequestBody BookDto bookDto) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(bookDto));
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        if (bookService.isExists(isbn)){
            bookEntity = bookService.saveBook(isbn, bookEntity);
            return new ResponseEntity<>(bookMapper.mapTo(bookEntity), HttpStatus.OK);
        }
        bookEntity = bookService.saveBook(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(bookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public Page<BookDto> findAllBooks(Pageable pageable){
        Page<BookEntity> bookEntitiesPage = bookService.findAll(pageable);
        return bookEntitiesPage.map(bookMapper::mapTo);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable String isbn){
        bookService.delete(isbn);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn){
        BookEntity bookEntity = bookService.getBook(isbn);
        return new ResponseEntity<>(bookMapper.mapTo(bookEntity), HttpStatus.OK);
    }

    @PatchMapping("books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn,
                                                     @RequestBody BookDto bookDto){
        if(!isbn.equals(bookDto.getIsbn())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        bookEntity = bookService.updateBook(bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(bookEntity), HttpStatus.ACCEPTED);
    }
}
