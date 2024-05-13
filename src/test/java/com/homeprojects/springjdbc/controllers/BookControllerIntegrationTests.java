package com.homeprojects.springjdbc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeprojects.springjdbc.TestDataUtil;
import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.domain.BookEntity;
import com.homeprojects.springjdbc.domain.dto.BookDto;
import com.homeprojects.springjdbc.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testBookCreateEndpointReturns200() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn()).content(objectMapper.writeValueAsString(bookDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testBookCreateEndpointReturnsBody() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn()).content(objectMapper.writeValueAsString(bookDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()));
    }

    @Test
    public void testFindAllBookReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindAllBooksRetrunsList() throws Exception {

        bookService.saveBook("iiii", TestDataUtil.createTestBookA(null));
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("PS1"));
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testFindBookByIsbnReturnsBook() throws Exception{
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.saveBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/"+bookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/books/"+bookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("PS1"));
    }

    @Test
    public void testUpdateBookEndPoint() throws Exception{
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.saveBook(bookEntity.getIsbn(), bookEntity);

        bookEntity.setTitle("Girl on a train");
        mockMvc.perform(MockMvcRequestBuilders.put("/books/"+bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookEntity)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Girl on a train"));
    }

    @Test
    public void testPartialUpdateOfBook() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        bookService.saveBook(bookEntity.getIsbn(), bookEntity);

        BookEntity partialUpdateBook = BookEntity.builder().isbn(bookEntity.getIsbn())
                .title("pudhusu").build();
        mockMvc.perform(MockMvcRequestBuilders.patch("/books/"+bookEntity.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partialUpdateBook)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("pudhusu"));
            }

    @Test
    public void testDeleteBook() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        bookEntity = bookService.saveBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/"+bookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .equals(!bookService.isExists(bookEntity.getIsbn()));
    }
}
