package com.example.member.domain.rental.controller;

import com.example.member.domain.rental.dto.request.RentalCreateReq;
import com.example.member.domain.rental.dto.response.RentalRes;
import com.example.member.domain.rental.service.RentalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;


    @PostMapping("/save")
    public String rent(@ModelAttribute RentalCreateReq req) {
        rentalService.rentBook(req);
        return "redirect:/rental/user/" + req.getUserId();
    }

    @GetMapping("/user/{userId}")
    public String userRentals(@PathVariable Long userId, Model model) {
        List<RentalRes> rentals = rentalService.getRentalsByUser(userId);
        model.addAttribute("rentals", rentals);
        return "rental/rental-list";
    }

    @PostMapping("/{rentalId}/return")
    public String returnBook(@PathVariable Long rentalId, @RequestParam Long userId) {
        rentalService.returnBook(rentalId);
        return "redirect:/rental/user/" + userId;
    }

    @PostMapping("/save-from-list")
    public String rentFromList(@RequestParam Long bookId, HttpSession session) {
        Long userId = (Long) session.getAttribute("loginId");

        if (userId == null) {
            return "redirect:/member/login"; // 로그인 안돼있으면 로그인 페이지로 이동
        }

        rentalService.rentBookFromList(userId, bookId);
        return "redirect:/rental/user/" + userId;
    }
}
