package com.example.member.global.init;

import com.example.member.book.entity.Author;
import com.example.member.book.entity.Genre;
import com.example.member.book.repository.AuthorRepository;
import com.example.member.book.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void run(String... args) {
        if (authorRepository.count() == 0) {
            authorRepository.save(Author.builder().name("조앤 K. 롤링").country("영국").build());
            authorRepository.save(Author.builder().name("히가시노 게이고").country("일본").build());
            authorRepository.save(Author.builder().name("조지 오웰").country("영국").build());
        }

        if (genreRepository.count() == 0) {
            genreRepository.save(Genre.builder().genreName("판타지").description("마법과 모험").build());
            genreRepository.save(Genre.builder().genreName("추리").description("범죄, 수사, 미스터리").build());
            genreRepository.save(Genre.builder().genreName("고전").description("문학사적으로 중요한 작품").build());
        }
    }
}