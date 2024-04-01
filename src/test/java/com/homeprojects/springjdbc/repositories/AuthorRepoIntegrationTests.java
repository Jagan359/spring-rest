package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.Author;
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
public class AuthorRepoIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepoIntegrationTests(AuthorRepository underTest){
        this.underTest = underTest;
    }
    @Test
    public void testAuthorIsCreatedInDbAndRecalled(){
        Author author = createTestAuthorA();
        underTest.save(author);

        Optional<Author> opAuthor = underTest.findById(author.getId());
        assertThat(opAuthor).isPresent();
        assertThat(opAuthor.get()).isEqualTo(author);
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndRetrieved(){
        Author authorA = createTestAuthorA();
        Author authorB = createTestAuthorB();
        Author authorC = createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> authors = underTest.findAll();

        assertThat(authors).hasSize(3).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testUpdateAuthorCreatesAuthor(){
        Author authorA = createTestAuthorA();

        underTest.save(authorA);

        authorA.setName("New Name");
        underTest.save(authorA);
        assertThat(underTest.findById(authorA.getId())).isPresent().contains(authorA);
    }

    @Test
    public void testAuthorCanBeDeleted(){
        Author author = createTestAuthorA();
        underTest.save(author);

        assertThat(underTest.findById(author.getId())).isPresent().contains(author);

        underTest.deleteById(author.getId());

        assertThat(underTest.findById(author.getId())).isEmpty();
    }

    @Test
    public void testTestGetAuthorAgedLessThan(){
        Author authorA = createTestAuthorA();
        underTest.save(authorA);
        Author authorB = createTestAuthorB();
        underTest.save(authorB);
        Author authorC = createTestAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.ageLessThan(20);

        assertThat(result).containsExactly(authorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        Author authorA = createTestAuthorA();
        authorA = underTest.save(authorA);
        Author authorB = createTestAuthorB();
        underTest.save(authorB);
        Author authorC = createTestAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(20);
        assertThat(result).containsExactly(authorA, authorB);

    }

}
