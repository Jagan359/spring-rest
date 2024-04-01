package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.Author;
import com.homeprojects.springjdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.homeprojects.springjdbc.TestDataUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepoIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookRepoIntegrationTests(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testBookCanBeCreatedAndRetrieved(){
        Author author = createTestAuthorA();
        Book book = createTestBookA(author);
        underTest.save(book);

        Optional<Book> bookOp = underTest.findById(book.getIsbn());

        assertThat(bookOp).isPresent();
        assertThat(bookOp.get()).isEqualTo(book);
    }

    @Test
    public void testMultuiplBooksCanBeCreateAndRetrieved(){
        Author author = createTestAuthorA();
        Book bookA = createTestBookA(author);

        Book bookB = createTestBookB(author);
        Book bookC = createTestBookC(author);
        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        Iterable<Book> books = underTest.findAll();

        assertThat(books).hasSize(3).containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testUpdateBookCanUpdateBookThatCanBeRetrieved(){
        Author author = createTestAuthorA();
        Book book = createTestBookA(author);
        underTest.save(book);
        book.setTitle("SS");

        underTest.save(book);

        assertThat(underTest.findById(book.getIsbn())).isPresent();
        assertThat(underTest.findById(book.getIsbn()).get().getTitle()).isEqualTo("SS");
    }

    @Test
    public void testDeleteActuallyDeltesBook(){
        Author author = createTestAuthorA();
        Book book = createTestBookA(author);
        underTest.save(book);

        assertThat(underTest.findById(book.getIsbn())).isPresent();

        underTest.deleteById(book.getIsbn());

        assertThat(underTest.findById(book.getIsbn())).isEmpty();
    }
}
