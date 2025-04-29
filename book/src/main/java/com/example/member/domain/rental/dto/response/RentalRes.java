package com.example.member.domain.rental.dto.response;

import com.example.member.domain.rental.entity.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRes {
    private Long rentalId;
    private String bookTitle;
    private String userEmail;
    private Long userId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private RentalStatus status;
}
