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



@GetMapping("/users/login")
public String returnUserLoginPage(){
    System.out.println("inside returnLoginPage method");
    return "userLogin";
}

@GetMapping("/company/login")
public String returnCompanyLoginPage(){
    return "companyLogin";
}


}
