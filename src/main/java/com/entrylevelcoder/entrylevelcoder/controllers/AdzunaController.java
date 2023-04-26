package com.entrylevelcoder.entrylevelcoder.controllers;

import com.entrylevelcoder.entrylevelcoder.services.AdzunaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

    @Controller
    public class AdzunaController {

        private AdzunaService adzunaService;

        public AdzunaController(AdzunaService adzunaService) {
            this.adzunaService = adzunaService;
        }

        @GetMapping("/posts")
        @ResponseBody
        public String returnJobs() {
            return adzunaService.returnJSON();
        }

    }

