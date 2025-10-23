package com.bookmanager.bookmanagement.repository;

import com.bookmanager.bookmanagement.entity.Book;
import com.bookmanager.bookmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUser(User user);
    List<Book> findByUserAndTitleContainingIgnoreCase(User user, String title);
    List<Book> findByUserAndAuthorContainingIgnoreCase(User user, String author);
    List<Book> findByUserAndGenreContainingIgnoreCase(User user, String genre);
}