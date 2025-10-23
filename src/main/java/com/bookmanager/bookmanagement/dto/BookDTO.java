package com.bookmanager.bookmanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private LocalDate publicationDate;
}