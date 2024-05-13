package com.homeprojects.springjdbc.repositories;

import com.homeprojects.springjdbc.domain.AuthorEntity;
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
public class AuthorRepoIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepoIntegrationTests(AuthorRepository underTest){
        this.underTest = underTest;
    }
    @Test
    public void testAuthorIsCreatedInDbAndRecalled(){
        AuthorEntity authorEntity = createTestAuthorA();
        underTest.save(authorEntity);

        Optional<AuthorEntity> opAuthor = underTest.findById(authorEntity.getId());
        assertThat(opAuthor).isPresent();
        assertThat(opAuthor.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndRetrieved(){
        AuthorEntity authorEntityA = createTestAuthorA();
        AuthorEntity authorEntityB = createTestAuthorB();
        AuthorEntity authorEntityC = createTestAuthorC();

        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> authors = underTest.findAll();

        assertThat(authors).hasSize(3).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testUpdateAuthorCreatesAuthor(){
        AuthorEntity authorEntityA = createTestAuthorA();

        underTest.save(authorEntityA);

        authorEntityA.setName("New Name");
        underTest.save(authorEntityA);
        assertThat(underTest.findById(authorEntityA.getId())).isPresent().contains(authorEntityA);
    }

    @Test
    public void testAuthorCanBeDeleted(){
        AuthorEntity authorEntity = createTestAuthorA();
        underTest.save(authorEntity);

        assertThat(underTest.findById(authorEntity.getId())).isPresent().contains(authorEntity);

        underTest.deleteById(authorEntity.getId());

        assertThat(underTest.findById(authorEntity.getId())).isEmpty();
    }

    @Test
    public void testTestGetAuthorAgedLessThan(){
        AuthorEntity authorEntityA = createTestAuthorA();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = createTestAuthorB();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = createTestAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(20);

        assertThat(result).containsExactly(authorEntityC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        AuthorEntity authorEntityA = createTestAuthorA();
        authorEntityA = underTest.save(authorEntityA);
        AuthorEntity authorEntityB = createTestAuthorB();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = createTestAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(20);
        assertThat(result).containsExactly(authorEntityA, authorEntityB);

    }

}
