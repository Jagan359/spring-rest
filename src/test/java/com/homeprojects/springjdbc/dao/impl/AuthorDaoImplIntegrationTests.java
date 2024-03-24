package com.homeprojects.springjdbc.dao.impl;

import com.homeprojects.springjdbc.domain.Author;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.homeprojects.springjdbc.TestDataUtil.createTestAuthor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest){
        this.underTest = underTest;
    }
    @Test
    public void testAuthorIsCreatedInDbAndRecalled(){
        Author author = createTestAuthor();
        underTest.create(author);

        Optional<Author> opAuthor = underTest.findOne(author.getId());
        assertThat(opAuthor).isPresent();
        assertThat(opAuthor.get()).isEqualTo(author);

    }
}
