package com.example.member.domain.rental.repository;

import com.example.member.domain.rental.entity.Rental;
import com.example.member.domain.user.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUser(MemberEntity user);
}
