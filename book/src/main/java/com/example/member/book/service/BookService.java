package com.example.member.book.service;

import com.example.member.book.dto.request.BookCreateReq;
import com.example.member.book.dto.response.BookRes;
import com.example.member.book.entity.Author;
import com.example.member.book.entity.Book;
import com.example.member.book.entity.BookGenre;
import com.example.member.book.entity.Genre;
import com.example.member.book.repository.AuthorRepository;
import com.example.member.book.repository.BookGenreRepository;
import com.example.member.book.repository.BookRepository;
import com.example.member.book.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookGenreRepository bookGenreRepository;

    @Transactional
    public Long createBook(BookCreateReq req) {
        Author author = authorRepository.findById(req.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("작가를 찾을 수 없습니다: " + req.getAuthorId()));

        Book book = Book.builder()
                .title(req.getTitle())
                .isbn(req.getIsbn())
                .stock(req.getStock())
                .publisher(req.getPublisher())
                .publishedAt(req.getPublishedAt())
                .author(author)
                .build();

        bookRepository.save(book);

        for (Long genreId : req.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("장르를 찾을 수 없습니다: " + genreId));
            BookGenre bookGenre = new BookGenre(book, genre);
            bookGenreRepository.save(bookGenre);
        }

        return book.getBookId();
    }

    @Transactional(readOnly = true)
    public List<BookRes> findAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(book -> new BookRes(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getBookGenres().stream()
                        .map(bg -> bg.getGenre().getGenreName())
                        .toList(),
                book.getStock()
        )).toList();
    }
}
