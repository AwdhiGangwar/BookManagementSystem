package com.bookmanager.bookmanagement.mapper;

import com.bookmanager.bookmanagement.dto.BookDTO;
import com.bookmanager.bookmanagement.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // Entity → DTO
    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublicationDate(book.getPublicationDate());
        return dto;
    }

    // DTO → Entity
    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());
        book.setPublicationDate(dto.getPublicationDate());
        return book;
    }
}
