package com.entrylevelcoder.entrylevelcoder.controllers;


import org.springframework.web.bind.annotation.GetMapping;

public class AuthenticationController {

@GetMapping("/login")
public String returnLoginPage(){
    return "userProfile";
}


}
