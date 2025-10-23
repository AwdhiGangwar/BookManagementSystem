package com.bookmanager.bookmanagement.service;

import com.bookmanager.bookmanagement.dto.BorrowedBookDTO;
import com.bookmanager.bookmanagement.entity.Book;
import com.bookmanager.bookmanagement.entity.BorrowedBook;
import com.bookmanager.bookmanagement.entity.User;
import com.bookmanager.bookmanagement.repository.BookRepository;
import com.bookmanager.bookmanagement.repository.BorrowedBookRepository;
import com.bookmanager.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public BorrowedBookDTO borrowBook(Long bookId) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);
        borrowedBook.setUser(user);
        borrowedBook.setReturnStatus(false);
        borrowedBook = borrowedBookRepository.save(borrowedBook);
        return mapToDTO(borrowedBook);
    }

    public List<BorrowedBookDTO> getBorrowingHistory() {
        User user = getCurrentUser();
        return borrowedBookRepository.findByUser(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private BorrowedBookDTO mapToDTO(BorrowedBook borrowedBook) {
        BorrowedBookDTO dto = new BorrowedBookDTO();
        dto.setId(borrowedBook.getId());
        dto.setBookId(borrowedBook.getBook().getId());
        dto.setUserId(borrowedBook.getUser().getId());
        dto.setBorrowedAt(borrowedBook.getBorrowedAt());
        dto.setReturnStatus(borrowedBook.isReturnStatus());
        return dto;
    }
}