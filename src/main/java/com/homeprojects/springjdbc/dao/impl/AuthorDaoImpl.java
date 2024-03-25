package com.homeprojects.springjdbc.dao.impl;

import com.homeprojects.springjdbc.dao.AuthorDao;
import com.homeprojects.springjdbc.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)", author.getId(), author.getName(),
                author.getAge());
    }

    public Optional<Author> findOne(long authorId) {
        List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(),
                authorId);
        return results.stream().findFirst();

    }

    @Override
    public List<Author> find() {
        List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors",
                new AuthorRowMapper());
        return results;
    }

    @Override
    public void update(Author authorA, Long id) {
        jdbcTemplate.update("UPDATE authors SET id = ?, name = ?, age = ?, where id =?", authorA.getId(),
                authorA.getName(), authorA.getAge(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
    }

    public  static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .name(rs.getString("name"))
                    .id(rs.getLong("id"))
                    .age(rs.getInt("age")).build();
        }
    }
}
