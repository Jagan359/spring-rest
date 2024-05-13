package com.homeprojects.springjdbc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeprojects.springjdbc.TestDataUtil;
import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.domain.BookEntity;
import com.homeprojects.springjdbc.domain.dto.AuthorDto;
import com.homeprojects.springjdbc.services.AuthorService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorCreatesAuthorSuccessfully() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorCreatesAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Dingu")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.age").isNumber()
        );
    }

    @Test
    public void testFindAllReturns200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testFindAllAuthorsReturnsList() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Dingu"));
    }
    @Test
    public void testFindByNameReturnsCorrectBody() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authorname/Dingu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("jagan"));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/2").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorEntity))).andExpect(MockMvcResultMatchers.status().isNotFound());

        authorEntity.setName("Jagan");
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorEntity)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jagan"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPartialUpdate() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/2").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorEntity))).andExpect(MockMvcResultMatchers.status().isNotFound());

        AuthorEntity partialAuthorEntity = AuthorEntity.builder().age(29).build();
        AuthorDto partialAuthorDto = AuthorDto.builder().age(29).build();
        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/"+authorEntity.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partialAuthorEntity))).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(29));

        mockMvc.perform(MockMvcRequestBuilders.get("/authorname/"+authorEntity.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(29))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Dingu"));

//        AuthorEntity partialUpdateAuthor = AuthorEntity.builder().id(1L).age(30).build();

    }

    @Test
    public void testDeleteAuthor() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorB();
        author = authorService.save(author);
        if(!authorService.isExists(author.getId()))
                throw new RuntimeException("Author does not exist");

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/"+author.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assert !authorService.isExists(author.getId());
    }
}
