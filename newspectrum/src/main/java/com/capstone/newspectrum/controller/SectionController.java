package com.capstone.newspectrum.controller;

import com.capstone.newspectrum.dto.IssueDTO;
import com.capstone.newspectrum.dto.TodayKeywordItemDTO;
import com.capstone.newspectrum.enumeration.Domain;
import com.capstone.newspectrum.service.SectionPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionPageService sectionPageService;

    @GetMapping("/politics")
    public String politicsPage(Model model) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        LocalDateTime startDate = today.minusDays(2);

        List<IssueDTO> issues = sectionPageService.get_issue_by_domain(startDate, today, Domain.정치);
        List<TodayKeywordItemDTO> keywords = sectionPageService.get_today_keyword_items_by_domain(startDate, today, Domain.정치);

        model.addAttribute("issues", issues);
        model.addAttribute("keywords", keywords);
        model.addAttribute("sectionName", "정치");

        return "section/politics"; // templates/section/politics.html
    }

    @GetMapping("/economy")
    public String economyPage(Model model) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        LocalDateTime startDate = today.minusDays(2);

        List<IssueDTO> issues = sectionPageService.get_issue_by_domain(startDate, today, Domain.경제);
        List<TodayKeywordItemDTO> keywords = sectionPageService.get_today_keyword_items_by_domain(startDate, today, Domain.경제);

        model.addAttribute("issues", issues);
        model.addAttribute("keywords", keywords);
        model.addAttribute("sectionName", "경제");

        return "section/economy"; // templates/section/economy.html
    }

    @GetMapping("/social")
    public String socialPage(Model model) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        LocalDateTime startDate = today.minusDays(2);

        List<IssueDTO> issues = sectionPageService.get_issue_by_domain(startDate, today, Domain.사회);
        List<TodayKeywordItemDTO> keywords = sectionPageService.get_today_keyword_items_by_domain(startDate, today, Domain.사회);

        model.addAttribute("issues", issues);
        model.addAttribute("keywords", keywords);
        model.addAttribute("sectionName", "사회");

        return "section/social"; // templates/section/social.html
    }

    @GetMapping("/entertainment")
    public String entertainmentPage(Model model) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        LocalDateTime startDate = today.minusDays(2);

        List<IssueDTO> issues = sectionPageService.get_issue_by_domain(startDate, today, Domain.연예);
        List<TodayKeywordItemDTO> keywords = sectionPageService.get_today_keyword_items_by_domain(startDate, today, Domain.연예);

        model.addAttribute("issues", issues);
        model.addAttribute("keywords", keywords);
        model.addAttribute("sectionName", "연예");

        return "section/entertainment"; // templates/section/entertainment.html
    }

    @GetMapping("/sports")
    public String sportsPage(Model model) {
        LocalDateTime today = LocalDateTime.of(2025, 2, 28, 23, 59);
        LocalDateTime startDate = today.minusDays(2);

        List<IssueDTO> issues = sectionPageService.get_issue_by_domain(startDate, today, Domain.스포츠);
        List<TodayKeywordItemDTO> keywords = sectionPageService.get_today_keyword_items_by_domain(startDate, today, Domain.스포츠);

        model.addAttribute("issues", issues);
        model.addAttribute("keywords", keywords);
        model.addAttribute("sectionName", "스포츠");

        return "section/sports"; // templates/section/sports.html
    }
}
