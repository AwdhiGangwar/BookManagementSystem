package com.bookmanager.bookmanagement.repository;

import com.bookmanager.bookmanagement.entity.BorrowedBook;
import com.bookmanager.bookmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUser(User user);
}