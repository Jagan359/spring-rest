package com.homeprojects.springjdbc.dao.impl;

import com.homeprojects.springjdbc.TestDataUtil;
import com.homeprojects.springjdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    public BookDaoImpl underTest;

    @Test
    public void testBookImplCreateCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("123-456-789"), eq("PS"), eq(123456L)
        );
    }

    @Test
    public void testFindOneBookGeneratesCorrectSql(){

        String isbn = "123-456";
        underTest.findOne(isbn);

        verify(jdbcTemplate).query(
                eq("SELECT isbn, author_id, title where isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq(isbn));
    }

    @Test
    public void testFindManyBookGeneratesCorrectSql(){

        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, author_id, title FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testUpdateBookGeneratesCorrectSQL(){
        Book book = TestDataUtil.createTestBookA();
        underTest.update(book, book.getIsbn());

        verify(jdbcTemplate).update("UPDATE books SET title = ?, isbn = ?, author_id = ? WHERE isbn = ?", book.getTitle(), book.getIsbn(), book.getAuthorId(), book.getIsbn());
    }

    @Test
    public void testDeleteGenereatesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();
        underTest.delete(book.getIsbn());

        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ?", book.getIsbn());
    }
}
