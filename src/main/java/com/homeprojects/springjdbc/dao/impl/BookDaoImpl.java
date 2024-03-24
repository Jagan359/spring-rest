package com.homeprojects.springjdbc.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
