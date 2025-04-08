package com.capstone.newspectrum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keyword")
public class KeywordController {

    @GetMapping("/keyword")
    public String keywordPage() {
        return "keyword/keyword"; // templates/keyword/keyword.html
    }
}
