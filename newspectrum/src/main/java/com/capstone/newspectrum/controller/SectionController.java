package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.NewsArticleDTO;
import com.capstone.newspectrum.dto.TodayKeywordItemDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.service.SectionPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionPageService sectionPageService;

    private LocalDateTime today = LocalDateTime.of(2025, 3, 1, 00, 00);

    @GetMapping("/politics")
    public String politicsPage(Model model) {
        LocalDateTime startDate = today.minusDays(2);

        List<NewsArticleDTO> news_article = sectionPageService.get_news_have_cluster_by_domain(startDate, today, Domain.정치);
        model.addAttribute("news_article", news_article);

        return "section/politics"; // templates/section/politics.html
    }

    @GetMapping("/economy")
    public String economyPage(Model model) {
        LocalDateTime startDate = today.minusDays(2);

        List<NewsArticleDTO> news_article = sectionPageService.get_news_have_cluster_by_domain(startDate, today, Domain.경제);
        model.addAttribute("news_article", news_article);

        return "section/economy"; // templates/section/economy.html
    }

    @GetMapping("/social")
    public String socialPage(Model model) {
        LocalDateTime startDate = today.minusDays(2);

        List<NewsArticleDTO> news_article = sectionPageService.get_news_have_cluster_by_domain(startDate, today, Domain.사회);
        model.addAttribute("news_article", news_article);

        return "section/social"; // templates/section/social.html
    }

    @GetMapping("/entertainment")
    public String entertainmentPage(Model model) {
        LocalDateTime startDate = today.minusDays(2);

        List<NewsArticleDTO> news_article = sectionPageService.get_news_have_cluster_by_domain(startDate, today, Domain.연예);
        model.addAttribute("news_article", news_article);

        return "section/entertainment"; // templates/section/entertainment.html
    }

    @GetMapping("/sports")
    public String sportsPage(Model model) {
        LocalDateTime startDate = today.minusDays(2);

        List<NewsArticleDTO> news_article = sectionPageService.get_news_have_cluster_by_domain(startDate, today, Domain.스포츠);
        model.addAttribute("news_article", news_article);

        return "section/sports"; // templates/section/sports.html
    }
}
