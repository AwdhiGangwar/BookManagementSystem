package com.bookmanager.bookmanagement.controller;

import com.bookmanager.bookmanagement.dto.BorrowedBookDTO;
import com.bookmanager.bookmanagement.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowed-books")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService borrowedBookService;

    @PostMapping("/{bookId}")
    public ResponseEntity<BorrowedBookDTO> borrowBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(borrowedBookService.borrowBook(bookId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<BorrowedBookDTO>> getBorrowingHistory() {
        return ResponseEntity.ok(borrowedBookService.getBorrowingHistory());
    }
}