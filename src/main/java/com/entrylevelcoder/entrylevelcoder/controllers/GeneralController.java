package com.entrylevelcoder.entrylevelcoder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/")
    public String landingPage() {
        return "index";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutUS";
    }

    @GetMapping("/contactus")
    public String contactUs() {
        return "contactUs";
    }



}