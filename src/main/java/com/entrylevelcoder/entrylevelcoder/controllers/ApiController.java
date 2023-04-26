package com.entrylevelcoder.entrylevelcoder.controllers;

import com.entrylevelcoder.entrylevelcoder.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @Value("${adzuna}")
    private String adzunaKey;

    @CrossOrigin(origins = "https://www.entrylevelcoder.com")
    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("adzuna", adzunaKey);
        return "index";
    }

    @CrossOrigin(origins = "https://www.entrylevelcoder.com")
    @GetMapping("/posts")
    public String returnJobs(Model model){
        model.addAttribute("adzuna", adzunaKey);
        return "browseJobs";
    }
}
