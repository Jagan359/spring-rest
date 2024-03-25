package com.homeprojects.springjdbc.dao.impl;

import com.homeprojects.springjdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.homeprojects.springjdbc.TestDataUtil.createTestAuthor;
import static com.homeprojects.springjdbc.TestDataUtil.createTestAuthorA;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreatesAuthorGeneratesCorrectSql(){
        Author author = createTestAuthor();
        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(123456L), eq("Dingu"), eq(33)
                );
    }

    @Test
    public void testFindOneGeneratesCorrectSql(){
        underTest.findOne(123456L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(123456L));
    }
    @Test
    public void testFindManyGeneratesCorrectSql(){
        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }

    @Test
    public void testUpdateCreatesAuthor(){
        Author authorA = createTestAuthorA();
        underTest.update(authorA, authorA.getId());

        verify(jdbcTemplate).update(
                eq("UPDATE authors SET id = ?, name = ?, age = ?, where id =?"),
                eq(authorA.getId()), eq(authorA.getName()), eq(authorA.getAge()), eq(authorA.getId()));
    }

    @Test
    public void testDeleteGeneratesCorrectSql(){
        underTest.delete(1L);

        verify(jdbcTemplate).update("DELETE FROM authors WHERE id = ?", 1L );
    }
}
