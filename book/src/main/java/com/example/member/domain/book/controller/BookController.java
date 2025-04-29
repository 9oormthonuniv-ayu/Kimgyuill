package com.example.member.domain.book.controller;

import com.example.member.domain.book.dto.request.BookCreateReq;
import com.example.member.domain.book.dto.response.BookRes;
import com.example.member.domain.author.repository.AuthorRepository;
import com.example.member.domain.genre.repository.GenreRepository;
import com.example.member.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping
    public String bookList(Model model) {
        List<BookRes> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "book/book-list";
    }

    @GetMapping("/form")
    public String bookForm(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book/book-form";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute BookCreateReq req) {
        bookService.createBook(req);
        return "redirect:/books";
    }
}

