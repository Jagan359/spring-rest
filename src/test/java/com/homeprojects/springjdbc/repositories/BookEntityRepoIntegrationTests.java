package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.domain.BookEntity;
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
public class BookEntityRepoIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepoIntegrationTests(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testBookCanBeCreatedAndRetrieved(){
        AuthorEntity authorEntity = createTestAuthorA();
        BookEntity bookEntity = createTestBookA(authorEntity);
        underTest.save(bookEntity);

        Optional<BookEntity> bookOp = underTest.findById(bookEntity.getIsbn());

        assertThat(bookOp).isPresent();
        assertThat(bookOp.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testMultuiplBooksCanBeCreateAndRetrieved(){
        AuthorEntity authorEntity = createTestAuthorA();
        BookEntity bookEntityA = createTestBookA(authorEntity);

        BookEntity bookEntityB = createTestBookB(authorEntity);
        BookEntity bookEntityC = createTestBookC(authorEntity);
        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        Iterable<BookEntity> books = underTest.findAll();

        assertThat(books).hasSize(3).containsExactly(bookEntityA, bookEntityB, bookEntityC);

    }

    @Test
    public void testUpdateBookCanUpdateBookThatCanBeRetrieved(){
        AuthorEntity authorEntity = createTestAuthorA();
        BookEntity bookEntity = createTestBookA(authorEntity);
        underTest.save(bookEntity);
        bookEntity.setTitle("SS");

        underTest.save(bookEntity);

        assertThat(underTest.findById(bookEntity.getIsbn())).isPresent();
        assertThat(underTest.findById(bookEntity.getIsbn()).get().getTitle()).isEqualTo("SS");
    }

    @Test
    public void testDeleteActuallyDeltesBook(){
        AuthorEntity authorEntity = createTestAuthorA();
        BookEntity bookEntity = createTestBookA(authorEntity);
        underTest.save(bookEntity);

        assertThat(underTest.findById(bookEntity.getIsbn())).isPresent();

        underTest.deleteById(bookEntity.getIsbn());

        assertThat(underTest.findById(bookEntity.getIsbn())).isEmpty();
    }
}
