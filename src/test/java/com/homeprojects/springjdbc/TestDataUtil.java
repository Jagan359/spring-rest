package com.homeprojects.springjdbc;

import com.homeprojects.springjdbc.domain.Author;
import com.homeprojects.springjdbc.domain.Book;
import org.checkerframework.checker.units.qual.A;

public class TestDataUtil {
    public static Author createTestAuthorA() {
        return Author.builder()
                .age(33).name("Dingu").build();
    }
    public static Author createTestAuthorB() {
        return Author.builder()
                .age(22).id(2L).name("Tooo").build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .age(13).id(3L).name("See").build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder().author(author).isbn("123-456-789").title("PS1").build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder().author(author).isbn("111-456-789").title("PS2").build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder().author(author).isbn("222-456-789").title("PS3").build();
    }
}
