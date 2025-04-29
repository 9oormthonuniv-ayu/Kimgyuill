package com.example.member.domain.book.entity;

import com.example.member.domain.genre.entity.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "book_genre")
public class BookGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookGenreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenre)) return false;
        BookGenre that = (BookGenre) o;
        return Objects.equals(book.getBookId(), that.book.getBookId()) &&
                Objects.equals(genre.getGenreId(), that.genre.getGenreId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(book.getBookId(), genre.getGenreId());
    }

    public BookGenre(Book book, Genre genre) {
        this.book = book;
        this.genre = genre;
    }
}