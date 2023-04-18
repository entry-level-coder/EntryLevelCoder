package com.entrylevelcoder.entrylevelcoder.controllers;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

@GetMapping("/login")
public String returnLoginPage(){
    return "userProfile";
}

    @PostMapping("/login")
    public String loginUser(HttpServletRequest request, Model model) {

        // Check if user is already logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            // Redirect to user profile view
            return "redirect:/user/profile";
        }

        return null;
    }
}
