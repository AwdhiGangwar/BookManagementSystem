package com.bookmanager.bookmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowedBookDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDateTime borrowedAt;
    private boolean returnStatus;
}