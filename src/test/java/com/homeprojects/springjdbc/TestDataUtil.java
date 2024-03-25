package com.homeprojects.springjdbc;

import com.homeprojects.springjdbc.domain.Author;
import com.homeprojects.springjdbc.domain.Book;

public class TestDataUtil {
    public static Author createTestAuthor() {
        return Author.builder()
                .age(33).id(123456L).name("Dingu").build();
    }    public static Author createTestAuthorA() {
        return Author.builder()
                .age(1).id(11111L).name("Aey").build();
    }    public static Author createTestAuthorB() {
        return Author.builder()
                .age(2).id(22222L).name("Tooo").build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .age(3).id(33333L).name("See").build();
    }

    public static Book createTestBookA() {
        return Book.builder().authorId(123456L).isbn("123-456-789").title("PS1").build();
    }

    public static Book createTestBookB() {
        return Book.builder().authorId(123456L).isbn("111-456-789").title("PS2").build();
    }

    public static Book createTestBookC() {
        return Book.builder().authorId(123456L).isbn("222-456-789").title("PS3").build();
    }
}
