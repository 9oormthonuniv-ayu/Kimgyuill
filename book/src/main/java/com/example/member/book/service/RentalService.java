package com.example.member.book.service;

import com.example.member.book.dto.request.RentalCreateReq;
import com.example.member.book.dto.response.RentalRes;
import com.example.member.book.entity.Book;
import com.example.member.book.entity.Rental;
import com.example.member.book.entity.RentalStatus;
import com.example.member.book.repository.BookRepository;
import com.example.member.book.repository.RentalRepository;
import com.example.member.user.domain.MemberEntity;
import com.example.member.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long rentBook(RentalCreateReq req) {
        Book book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다."));

        if (book.getStock() <= 0) {
            throw new IllegalStateException("도서 재고가 부족합니다.");
        }

        MemberEntity user = memberRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Rental rental = Rental.builder()
                .book(book)
                .user(user)
                .rentalDate(LocalDate.now())
                .dueDate(req.getDueDate())
                .status(RentalStatus.RENTED)
                .build();

        book.setStock(book.getStock() - 1); // 재고 감소
        rentalRepository.save(rental);
        return rental.getRentalId();
    }


    @Transactional
    public void returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("대여 정보를 찾을 수 없습니다."));

        if (rental.getStatus() == RentalStatus.RETURNED) {
            throw new IllegalStateException("이미 반납된 도서입니다.");
        }

        rental.setReturnDate(LocalDate.now());
        rental.setStatus(RentalStatus.RETURNED);

        Book book = rental.getBook();
        book.setStock(book.getStock() + 1); // 재고 복구
    }

    @Transactional(readOnly = true)
    public List<RentalRes> getRentalsByUser(Long userId) {
        MemberEntity user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        List<Rental> rentals = rentalRepository.findByUser(user);

        return rentals.stream().map(rental -> new RentalRes(
                rental.getRentalId(),
                rental.getBook().getTitle(),
                user.getMemberEmail(),
                user.getId(),
                rental.getRentalDate(),
                rental.getDueDate(),
                rental.getReturnDate(),
                rental.getStatus()
        )).toList();
    }

    @Transactional
    public Long rentBookFromList(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다."));

        if (book.getStock() <= 0) {
            throw new IllegalStateException("도서 재고가 부족합니다.");
        }

        MemberEntity user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Rental rental = Rental.builder()
                .book(book)
                .user(user)
                .rentalDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(7)) // 자동 7일 뒤로
                .status(RentalStatus.RENTED)
                .build();

        book.setStock(book.getStock() - 1); // 재고 감소
        rentalRepository.save(rental);
        return rental.getRentalId();
    }
}
