package com.entrylevelcoder.entrylevelcoder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdzunaController {
    private AdzunaSvc adzunaSvc;

    public AdzunaController(AdzunaSvc adzunaSvc) {
        this.adzunaSvc = adzunaSvc;
    }

    @GetMapping("/adzuna")
    @ResponseBody
    public String returnJobs() {
        return adzunaSvc.returnJSON();
    }

}
