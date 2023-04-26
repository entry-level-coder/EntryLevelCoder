package com.entrylevelcoder.entrylevelcoder.controllers;

import com.entrylevelcoder.entrylevelcoder.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @Value("${adzuna.key}")
    private String adzunaKey;

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("adzuna.key", adzunaKey);
        return "index";
    }


    @GetMapping("/posts")
    public String returnJobs(Model model){
        model.addAttribute("adzuna.key", adzunaKey);
        return "browseJobs";
    }
}
