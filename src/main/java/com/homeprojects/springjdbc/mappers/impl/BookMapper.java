package com.homeprojects.springjdbc.mappers.impl;

import com.homeprojects.springjdbc.domain.BookEntity;
import com.homeprojects.springjdbc.domain.dto.BookDto;
import com.homeprojects.springjdbc.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    ModelMapper modelMapper;

    BookMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
