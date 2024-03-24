package com.homeprojects.springjdbc.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

public class AuthorDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
