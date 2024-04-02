package com.homeprojects.springjdbc;

import com.homeprojects.springjdbc.domain.AuthorEntity;
import com.homeprojects.springjdbc.domain.BookEntity;

public class TestDataUtil {
    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .age(33).name("Dingu").build();
    }
    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .age(22).id(2L).name("Tooo").build();
    }
    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .age(13).id(3L).name("See").build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder().authorEntity(authorEntity).isbn("123-456-789").title("PS1").build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder().authorEntity(authorEntity).isbn("111-456-789").title("PS2").build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder().authorEntity(authorEntity).isbn("222-456-789").title("PS3").build();
    }
}
