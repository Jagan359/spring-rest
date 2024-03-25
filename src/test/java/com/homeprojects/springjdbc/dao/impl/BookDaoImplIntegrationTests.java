package com.homeprojects.springjdbc.dao.impl;

import com.homeprojects.springjdbc.domain.Author;
import com.homeprojects.springjdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.homeprojects.springjdbc.TestDataUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;
    private AuthorDaoImpl authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDaoImpl authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testBookCanBeCreatedAndRetrieved(){
        Author author = createTestAuthor();
        authorDao.create(author);
        Book book = createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        Optional<Book> bookOp = underTest.findOne(book.getIsbn());

        assertThat(bookOp).isPresent();
        assertThat(bookOp.get()).isEqualTo(book);
    }

    @Test
    public void testMultuiplBooksCanBeCreateAndRetrieved(){
        Author author = createTestAuthor();
        authorDao.create(author);
        Book bookA = createTestBookA();
        bookA.setAuthorId(author.getId());
        Book bookB = createTestBookB();
        bookA.setAuthorId(author.getId());
        Book bookC = createTestBookC();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);

        List<Book> books = underTest.find();

        assertThat(books).hasSize(3).containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testUpdateBookCanUpdateBookThatCanBeRetrieved(){
        Author author = createTestAuthor();
        authorDao.create(author);
        Book book = createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        book.setTitle("SS");

        underTest.update(book, book.getIsbn());

        assertThat(underTest.findOne(book.getIsbn())).isPresent();
        assertThat(underTest.findOne(book.getIsbn()).get().getTitle()).isEqualTo("SS");
    }

    @Test
    public void testDeleteActuallyDeltesBook(){
        Author author = createTestAuthor();
        authorDao.create(author);
        Book book = createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        assertThat(underTest.findOne(book.getIsbn())).isPresent();

        underTest.delete(book.getIsbn());

        assertThat(underTest.findOne(book.getIsbn())).isEmpty();


    }
}
