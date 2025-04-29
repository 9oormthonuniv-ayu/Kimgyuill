package com.example.member.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRes {
    private Long bookId;
    private String title;
    private String authorName;
    private List<String> genres;
    private int stock;
}
