package com.capstone.newspectrum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/section")
public class SectionController {

    @GetMapping("/politics")
    public String politicsPage() {
        return "section/politics"; // templates/section/politics.html
    }

    @GetMapping("/economy")
    public String economyPage() {
        return "section/economy"; // templates/section/economy.html
    }

    @GetMapping("/social")
    public String socialPage() {
        return "section/social"; // templates/section/social.html
    }

    @GetMapping("/entertainment")
    public String entertainmentPage() {
        return "section/entertainment"; // templates/section/entertainment.html
    }

    @GetMapping("/sports")
    public String sportsPage() {
        return "section/sports"; // templates/section/sports.html
    }
}
