package com.example.member.book.repository;

import com.example.member.book.entity.Rental;
import com.example.member.user.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUser(MemberEntity user);
}
