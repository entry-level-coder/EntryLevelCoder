package com.entrylevelcoder.entrylevelcoder.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {


    @GetMapping("/login")
    public String returnUserLoginPage() {
        return "login";

    }

}
