package com.bookmanager.bookmanagement.service;

import com.bookmanager.bookmanagement.dto.BookDTO;
import com.bookmanager.bookmanagement.entity.Book;
import com.bookmanager.bookmanagement.entity.User;
import com.bookmanager.bookmanagement.repository.BookRepository;
import com.bookmanager.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public BookDTO addBook(BookDTO bookDTO) {
        User user = getCurrentUser();
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setUser(user);
        book = bookRepository.save(book);
        return mapToDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book = bookRepository.save(book);
        return mapToDTO(book);
    }

    public void deleteBook(Long id) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        bookRepository.delete(book);
    }

    public List<BookDTO> getUserBooks() {
        User user = getCurrentUser();
        return bookRepository.findByUser(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> searchBooks(String title, String author, String genre) {
        User user = getCurrentUser();
        if (title != null) {
            return bookRepository.findByUserAndTitleContainingIgnoreCase(user, title).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } else if (author != null) {
            return bookRepository.findByUserAndAuthorContainingIgnoreCase(user, author).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } else if (genre != null) {
            return bookRepository.findByUserAndGenreContainingIgnoreCase(user, genre).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }
        return getUserBooks();
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setPublicationDate(book.getPublicationDate());
        return bookDTO;
    }
}