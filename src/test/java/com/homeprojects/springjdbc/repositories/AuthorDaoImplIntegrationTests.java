//package com.homeprojects.springjdbc.repositories;
//
//import com.homeprojects.springjdbc.domain.Author;
//import org.checkerframework.checker.units.qual.A;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.homeprojects.springjdbc.TestDataUtil.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class AuthorDaoImplIntegrationTests {
//
//    private AuthorDaoImpl underTest;
//
//    @Autowired
//    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest){
//        this.underTest = underTest;
//    }
//    @Test
//    public void testAuthorIsCreatedInDbAndRecalled(){
//        Author author = createTestAuthor();
//        underTest.create(author);
//
//        Optional<Author> opAuthor = underTest.findOne(author.getId());
//        assertThat(opAuthor).isPresent();
//        assertThat(opAuthor.get()).isEqualTo(author);
//    }
//
//
//    @Test
//    public void testMultipleAuthorsCanBeCreatedAndRetrieved(){
//        Author authorA = createTestAuthorA();
//        Author authorB = createTestAuthorB();
//        Author authorC = createTestAuthorC();
//
//        underTest.create(authorA);
//        underTest.create(authorB);
//        underTest.create(authorC);
//
//        List<Author> authors = underTest.find();
//
//        assertThat(authors).hasSize(3).containsExactly(authorA, authorB, authorC);
//    }
//
//    @Test
//    public void testUpdateAuthorCreatesAuthor(){
//        Author authorA = createTestAuthorA();
//        Long id = authorA.getId();
//
//        underTest.create(authorA);
//
//        assertThat(underTest.findOne(authorA.getId())).isPresent().contains(authorA);
//    }
//
//    @Test
//    public void testAuthorCanBeDeleted(){
//        Author author = createTestAuthor();
//        underTest.create(author);
//
//        underTest.findOne(author.getId());
//        assertThat(underTest.findOne(author.getId())).isPresent().contains(author);
//
//        underTest.delete(author.getId());
//
//        assertThat(underTest.findOne(author.getId())).isEmpty();
//    }
//}
