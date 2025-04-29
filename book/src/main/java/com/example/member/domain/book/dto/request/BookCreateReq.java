package com.example.member.domain.book.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookCreateReq {
    private String title;
    private String isbn;
    private int stock;
    private String publisher;
    private LocalDate publishedAt;
    private Long authorId;
    private List<Long> genreIds;
}
