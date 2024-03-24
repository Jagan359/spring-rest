package com.homeprojects.springjdbc;

import com.homeprojects.springjdbc.domain.Author;

public class TestDataUtil {
    public static Author createTestAuthor() {
        return Author.builder()
                .age(33).id(123456L).name("Dingu").build();
    }
}
